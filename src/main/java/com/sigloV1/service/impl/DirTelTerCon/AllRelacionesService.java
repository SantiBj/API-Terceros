package com.sigloV1.service.impl.DirTelTerCon;

import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerConRepository;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerRepository;
import com.sigloV1.service.interfaces.direccionesTelefonos.IAllRelacionesService;
import com.sigloV1.service.logica.DirTelTerCont.Logic;
import com.sigloV1.web.dtos.req.DirTelTerCon.DirOrTelTerDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.EDato;
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
    private DirTelTerConRepository dirTelTerConRepository;

    @Autowired
    private Logic logic;

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

    @Override
    public void estadoDatosContacto(EDato dato, Long idRelacionContacto) {

    }

    @Override
    public void crearDirOrTelefonoTer(DirOrTelTerDTO data, Boolean isContacto) {
        if (data.getDireccion() != null && data.getTelefono() == null
                && data.getDireccionTelefonos() == null ){
            logic.crearDireccionAndRelacionar(data.getDireccion(), data.getIdTercero(), isContacto);
        }else if (data.getDireccion() == null && data.getTelefono() != null
                && data.getDireccionTelefonos() == null ){
            logic.crearTelefonoAndRelacionar(data.getTelefono(), data.getIdTercero(), isContacto);
        }else if (data.getDireccion() == null && data.getTelefono() == null
                && data.getDireccionTelefonos() != null && !isContacto ){
            logic.crearDireccionAndTelefonosAndRelacionar(data.getDireccionTelefonos(),data.getIdTercero());
        }else if (data.getDireccion() == null && data.getTelefono() == null && data.getDireccionTelefonos() != null && isContacto){
            //aca la de contacto
        }
    }
}
