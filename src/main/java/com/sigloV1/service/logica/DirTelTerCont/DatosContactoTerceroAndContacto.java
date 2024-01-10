package com.sigloV1.service.logica.DirTelTerCont;

import com.sigloV1.dao.models.DirTelTerContEntity;
import com.sigloV1.dao.models.DirTelTerEntity;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerConRepository;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerRepository;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatosContactoTerceroAndContacto {

    @Autowired
    private DirTelTerRepository datosContactoTercero;

    @Autowired
    private DirTelTerConRepository datosContactoContacto;


    public DirTelTerEntity obtenerRelacionTercero(Long relacionId){
        return datosContactoTercero.findById(relacionId).
                orElseThrow(()-> new BadRequestCustom("La relacion entre los datos de contacto y el tercero no existe"));
    }

    public DirTelTerContEntity obtenerRelacionContacto(Long relacionId){
        return datosContactoContacto.findById(relacionId)
                .orElseThrow(()->new BadRequestCustom("La relacion los datos de contacto y el contacto no existe"));
    }

}
