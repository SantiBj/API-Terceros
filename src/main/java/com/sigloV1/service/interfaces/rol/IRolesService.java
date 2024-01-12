package com.sigloV1.service.interfaces.rol;

import com.sigloV1.dao.models.RolEntity;
import com.sigloV1.web.dtos.req.rol.RolAsociacionesReqDTO;
import com.sigloV1.web.dtos.req.rol.RolReqDTO;
import com.sigloV1.web.dtos.res.rol.RolRelacionResDTO;
import com.sigloV1.web.dtos.res.rol.RolResDTO;

import java.util.List;

public interface IRolesService {

    //listado de roles rol_tipo_tercero segun el tipo de tercero
    List<RolRelacionResDTO> rolesPorTipoTercero(Long tipoTerceroId);

    //creacion y asociacion de un rol(asociarlo con un tipo de rol) a un tipo de tercero ya existente
    void crearRolAsociado(RolAsociacionesReqDTO rolData);

    RolEntity editarRol(RolReqDTO rolData);

    void addTipoTercero(Long tipoTerceroId,Long rolId);
}
