package com.sigloV1.service.impl.DirTelTerCon;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerConRepository;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerRepository;
import com.sigloV1.dao.repositories.contacto.ContactoRepository;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.interfaces.direccionesTelefonos.IAllRelacionesService;
import com.sigloV1.service.logica.DirTelTerCont.DatosContactoTerceroAndContacto;
import com.sigloV1.service.logica.DirTelTerCont.LogicConsulta;
import com.sigloV1.service.logica.DirTelTerCont.LogicCreacion;
import com.sigloV1.service.logica.contacto.MetodosContacto;
import com.sigloV1.web.dtos.req.DirTelTerCon.*;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionTelefonosResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionesAndTelefonosDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.TelefonoResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AllRelacionesService implements IAllRelacionesService {
    @Autowired
    private LogicCreacion logicCreacion;

    @Autowired
    private LogicConsulta logicConsulta;

    @Autowired
    private TerceroAdapter terceroAdapter;

    @Autowired
    private DatosContactoTerceroAndContacto datosContacto;

    @Autowired
    private MetodosContacto metodosContacto;

    @Autowired
    private DirTelTerRepository datosContactoTercero;

    @Autowired
    private DirTelTerConRepository datosContactoContacto;


    public void crearDatosDeContacto(DatosDeContactoDTO datos) {
        if (datos.getDireccion() != null) {
            logicCreacion.crearDireccionAsociarTercero(datos.getDireccion(), null, null);
        } else if (datos.getTelefono() != null) {
            logicCreacion.crearTelefonoAsociarTercero(datos.getTelefono(), null, null);
        } else if (datos.getDireccionTelefonos() != null) {
            logicCreacion.crearTelefonosAsociarNuevaDireccion(datos.getDireccionTelefonos(), null, null);
        } else if (datos.getDireccionIdTelefonos() != null) {
            logicCreacion.crearTelefonosAsociarDireccionExistente(datos.getDireccionIdTelefonos(), null, null);
        }
    }


    public DireccionesAndTelefonosDTO direccionTelefonosTercero(Long terceroId) {
        TerceroEntity tercero = terceroAdapter.obtenerTerceroOException(terceroId);
        return logicConsulta.datosDeContactoTercero(tercero);
    }


    public DireccionesAndTelefonosDTO direccionesTelefonosContacto(Long contactoId) {
        ContactoEntity contacto = metodosContacto.obtenerContactoOException(contactoId);
        return logicConsulta.obtenerDatosDeContactoContacto(contacto);
    }

    public void estadoDireccionTercero(Long relacionId, Boolean estado) {
        //si se desactiva la direccion se desactivan todos los telefonos asociados a esta
        //si se activa la direccion se activan todos los telefonos asociados a esta
        DirTelTerEntity relacion = datosContacto.obtenerRelacionTercero(relacionId);
        if (relacion.getUsadaEnContacto())
            throw new BadRequestCustom("La relacion no pertenece a un tercero, pertenece a un contacto.");

        List<DirTelTerEntity> telefonoDireccion = datosContactoTercero
                .findByTerceroAndDireccionAndUsadaEnContacto(relacion.getTercero(), relacion.getDireccion(), false);

        telefonoDireccion.forEach(dc -> {
            dc.setEstadoDireccion(estado);
            dc.setEstadoTelefono(estado);
            datosContactoTercero.save(dc);
        });
    }

    public void estadoDireccionContacto(Long relacionId, Boolean estado) {
        DirTelTerContEntity relacion = datosContacto.obtenerRelacionContacto(relacionId);
        //todas las relaciones que tenga la misma direccion y esten asociadas a este contacto
        List<DirTelTerEntity> relacionesWithDireccion = datosContactoTercero
                .findByTerceroAndDireccionAndUsadaEnContacto(
                        relacion.getContacto().getContacto()
                        , relacion.getDireccionTelefono().getDireccion()
                        , true);

        //cuales de estas relaciones del contacto se usan para esta empresa especifica
        relacionesWithDireccion
                .forEach(rd -> {
                    datosContactoContacto.findByDireccionTelefonoAndContacto(rd, relacion.getContacto())
                            .ifPresent(value -> {
                                value.setEstadoDireccion(estado);
                                value.setEstadoTelefono(estado);
                                datosContactoContacto.save(value);
                            });
                });
    }

    public void estadoTelefonoTercero(Long relacionId, Boolean estado) {
        DirTelTerEntity relacion = datosContacto.obtenerRelacionTercero(relacionId);
        if (relacion.getUsadaEnContacto())
            throw new BadRequestCustom("La relacion no pertenece a un tercero, pertenece a un contacto.");
        relacion.setEstadoTelefono(estado);
        datosContactoTercero.save(relacion);
    }


    public void estadoTelefonoContacto(Long relacionId, Boolean estado) {
        DirTelTerContEntity relacion = datosContacto.obtenerRelacionContacto(relacionId);
        relacion.setEstadoTelefono(estado);
        datosContactoContacto.save(relacion);
    }

    public void eliminarRelacionTercero(Long relacionId) {
        DirTelTerEntity relacion = datosContacto.obtenerRelacionTercero(relacionId);
        if (relacion.getUsadaEnContacto())
            throw new BadRequestCustom("La relacion no pertenece a un tercero, pertenece a un contacto.");
        datosContactoTercero.delete(relacion);
    }

    public void eliminarRelacionContacto(Long relacionId) {
        DirTelTerContEntity relacion = datosContacto.obtenerRelacionContacto(relacionId);
        Integer cantidadRelacionesWithDireccion = datosContactoContacto.findByDireccionTelefono(relacion.getDireccionTelefono()).size();

        if (cantidadRelacionesWithDireccion == 1) {
            datosContactoTercero.delete(relacion.getDireccionTelefono());
        }
        datosContactoContacto.delete(relacion);
    }

}
