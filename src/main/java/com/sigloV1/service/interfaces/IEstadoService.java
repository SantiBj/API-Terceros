package com.sigloV1.service.interfaces;

import com.sigloV1.web.dtos.req.estado.EstadoDTOReqBiPais;
import com.sigloV1.web.dtos.req.estado.EstadoDTOReqPais;
import com.sigloV1.web.dtos.res.estado.EstadoDTOResBiPais;
import com.sigloV1.web.dtos.res.pais.EstadoDTOResBi;
import com.sigloV1.web.dtos.res.estado.EstadoDTOResPais;

public interface IEstadoService {
    EstadoDTOResBiPais obtenerEstado(Long id);

    EstadoDTOResBiPais crearEstadoBi(EstadoDTOReqBiPais estado);

    EstadoDTOResPais crearEstado(EstadoDTOReqPais estado);

    void eliminarEstado(Long id);

    EstadoDTOResPais editarEstado(EstadoDTOReqPais estado, Long id);

}
