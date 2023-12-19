package com.sigloV1.service.impl.DirTelTerCon;

import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerRepository;
import com.sigloV1.service.interfaces.direccionesTelefonos.IAllRelacionesService;
import com.sigloV1.service.logica.DirTelTerCont.LogicCreacion;
import com.sigloV1.web.dtos.req.DirTelTerCon.*;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionTelefonosResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.TelefonoResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllRelacionesService implements IAllRelacionesService {

    @Autowired
    private DirTelTerRepository dirTelTerRepository;

    @Autowired
    private LogicCreacion logicCreacion;


    @Override
    public List<DireccionTelefonosResDTO> direccionesTercero(Long idTercero) {
        return null;
    }

    @Override
    public List<DireccionTelefonosResDTO> dirTelRolContacto(Long idTercero) {
        return null;
    }

    @Override
    public List<TelefonoResDTO> telefonosSinDireccionTercero(Long idTercero) {
        return null;
    }

    @Override
    public void estadoDatosTercero(EDato dato, Long relacionId) {

    }

    public void crearDatosDeContacto(DatosDeContactoDTO datos){
        if (datos.getDireccion() != null){
            logicCreacion.crearDireccionAsociarTercero(datos.getDireccion());
        }else if(datos.getTelefono() != null){
            logicCreacion.crearTelefonoAsociarTercero(datos.getTelefono());
        }else if(datos.getDireccionTelefonos() != null){
            logicCreacion.crearTelefonosAsociarNuevaDireccion(datos.getDireccionTelefonos());
        }else if(datos.getDireccionIdTelefonos() != null){
            logicCreacion.crearTelefonosAsociarDireccionExistente(datos.getDireccionIdTelefonos());
        }
    }

}
