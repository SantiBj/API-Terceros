package com.sigloV1.service.interfaces.direccionesTelefonos;

import com.sigloV1.web.dtos.req.DirTelTerCon.DatosDeContactoDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.EDato;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionTelefonosResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.TelefonoResDTO;

import java.util.List;

public interface IAllRelacionesService {

    // solo devolver las que contacto sea false
    // aca de volver los datos con el id de la relacion con el tercero
    List<DireccionTelefonosResDTO> direccionesTercero(Long idTercero);

    //aca ya toca consultar la tabla dirTelTerCon para sacar estados
    List<DireccionTelefonosResDTO> dirTelRolContacto(Long idTercero);

    List<TelefonoResDTO> telefonosSinDireccionTercero(Long idTercero);

    //desactivar telefono
    void estadoDatosTercero(EDato dato,Long relacionId);

    void crearDatosDeContacto(DatosDeContactoDTO datos);
}
