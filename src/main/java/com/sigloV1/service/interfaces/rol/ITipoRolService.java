package com.sigloV1.service.interfaces.rol;

import com.sigloV1.web.dtos.req.rol.TipoRolReqDTO;
import com.sigloV1.web.dtos.res.rol.TipoRolResDTO;

import java.util.List;

public interface ITipoRolService {
    List<TipoRolResDTO> allTipoRoles();

    void guardarTipoRol(TipoRolReqDTO tipoRolData);

    TipoRolResDTO editarNombreTipoRol(TipoRolReqDTO tipoRolNuevo, Long tipoRolId);

    void eliminarTipoRol(Long tipoRolId);
}
