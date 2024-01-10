package com.sigloV1.service.interfaces.direccionesTelefonos;

import com.sigloV1.web.dtos.req.DirTelTerCon.DatosDeContactoDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.EDato;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionTelefonosResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionesAndTelefonosDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.TelefonoResDTO;

import java.util.List;

public interface IAllRelacionesService {

    DireccionesAndTelefonosDTO direccionTelefonosTercero(Long terceroId);

    DireccionesAndTelefonosDTO direccionesTelefonosContacto(Long contactoId);

    void crearDatosDeContacto(DatosDeContactoDTO datos);

    void estadoDireccionTercero(Long relacionId,Boolean estado);

    void estadoDireccionContacto(Long relacionId,Boolean estado);

    void estadoTelefonoTercero(Long relacionId,Boolean estado);

    void estadoTelefonoContacto(Long relacionId,Boolean estado);

    void eliminarRelacionTercero(Long relacionId);

    void eliminarRelacionContacto(Long relacionId);
}
