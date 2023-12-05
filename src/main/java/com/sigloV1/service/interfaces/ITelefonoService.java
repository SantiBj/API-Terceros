package com.sigloV1.service.interfaces;

import com.sigloV1.dao.models.TerceroTelefonoEntity;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;
import com.sigloV1.web.dtos.res.telefono.TelefonoResDTO;

import java.util.List;

public interface ITelefonoService {

    List<TelefonoResDTO> crearTelefonosTercero(List<TelefonoReqDTO> telefonos, Long tercero);
    Boolean estadoTelefonoTercero(Long idTelefono,Long idTercero,Boolean estado);
    TelefonoResDTO editarTelefono(Long idTelefono, Long idTercero,TelefonoReqDTO datos);
    List<TelefonoResDTO> obtenerTelefonosTercero(Long idTercero);

    void eliminarTelefonoTercero(Long idTelefono, Long idTercero, Long idDireccion);
}
