package com.sigloV1.service.adapters;


import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.TelefonoReqDTO;

public interface DatosContactoAdapter {

    void crearDireccionAsociarTercero(DireccionReqDTO data,
                                      TerceroEntity terceroInstance, ContactoEntity contactoEntity);
    void crearTelefonoAsociarTercero(TelefonoReqDTO data,
                                     TerceroEntity terceroInstance, ContactoEntity contactoEntity);
    void crearTelefonosAsociarNuevaDireccion(DireccionTelefonosReqDTO dataDireccion,
                                             TerceroEntity terceroInstance, ContactoEntity contactoEntity);
}
