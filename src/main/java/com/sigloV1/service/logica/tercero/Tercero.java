package com.sigloV1.service.logica.tercero;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.DocDetallesRep;
import com.sigloV1.dao.repositories.tercero.TerceroRepository;
import com.sigloV1.dao.repositories.tercero.TerceroRolTipoTerRepository;
import com.sigloV1.dao.repositories.tercero.TipoTeceroRepository;
import com.sigloV1.service.adapters.*;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.TelefonoReqDTO;
import com.sigloV1.web.dtos.req.email.EmailReqDTO;
import com.sigloV1.web.dtos.req.tercero.*;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Tercero {

    @Autowired
    private DocDetallesRep docDetallesRep;

    @Autowired
    private PaisAdapter paisAdapter;

    @Autowired
    private TipoTerceroAdapter tipoTerceroAdapter;

    @Autowired
    private TipoTeceroRepository tipoTeceroRepository;

    @Autowired
    private TerceroRepository terceroRepository;


    @Autowired
    private DatosContactoAdapter datosContactoAdapter;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RolAdapter rolAdapter;

    @Autowired
    private EmailCreacionAdapter emailCreacionAdapter;

    @Autowired
    private TerceroAdapter validacionExistencia;



    public <T> TerceroEntity crearTercero(T data,Long docDetallesId){
        DocDetallesEntity docDetalles = docDetallesRep.findById(docDetallesId)
                .orElseThrow(()->new BadRequestCustom("El tipo de documento no existe."));

        if (data instanceof TerceroContactoDTO){
            TerceroContactoDTO dataTerceroContacto = (TerceroContactoDTO) data;

            Optional<TerceroEntity> terceroContactoExistente = terceroRepository.personaExistente(
                    dataTerceroContacto.getPais(),
                    dataTerceroContacto.getIdentificacion(),
                    dataTerceroContacto.getDocDetalles(),
                    dataTerceroContacto.getNombre()
            );

            if (terceroContactoExistente.isPresent()){
                return terceroContactoExistente.get();
            }

            TipoTerceroEntity personaNaturalEntity = tipoTeceroRepository.findByNombreIgnoreCase("NATURAL")
                    .orElseThrow(()->new BadRequestCustom("El tipo de persona NATURAL debe existir."));

            return terceroRepository.save(TerceroEntity
                    .builder()
                            .identificacion(dataTerceroContacto.getIdentificacion())
                            .nombre(dataTerceroContacto.getNombre())
                            .pais(paisAdapter.obtenerPaisOException(dataTerceroContacto.getPais()))
                            .docDetalles(docDetalles)
                            .tipoTercero(personaNaturalEntity)
                            .fechaExpedicion(dataTerceroContacto.getFechaExpedicion())
                    .build());

        }else{
            TerceroReqDTO dataTerceroEmpresa = (TerceroReqDTO) data;

            terceroRepository.empresaExistente(
                    dataTerceroEmpresa.getPais(),
                    dataTerceroEmpresa.getIdentificacion(),
                    dataTerceroEmpresa.getDocDetalles(),
                    dataTerceroEmpresa.getNombreComercial(),
                    dataTerceroEmpresa.getRazonSocial(),
                    dataTerceroEmpresa.getTerceroPadre(),
                    dataTerceroEmpresa.getTipoTercero()
            ).ifPresent((value)-> {throw new BadRequestCustom("El tercero que desea crear ya existe.");});


            return terceroRepository.save(TerceroEntity.builder()
                    .identificacion(dataTerceroEmpresa.getIdentificacion())
                    .razonSocial(dataTerceroEmpresa.getRazonSocial())
                    .nombreComercial(dataTerceroEmpresa.getNombreComercial())
                    .pais(paisAdapter.obtenerPaisOException(dataTerceroEmpresa.getPais()))
                    .docDetalles(docDetalles)
                    .terceroPadre(
                            dataTerceroEmpresa.getTerceroPadre() != null
                                    ? validacionExistencia.obtenerTerceroOException(dataTerceroEmpresa.getTerceroPadre())
                                    : null
                    )
                    .tipoTercero(tipoTerceroAdapter
                            .obtenerTerceroOException(dataTerceroEmpresa.getTipoTercero()))
                    .fechaExpedicion(dataTerceroEmpresa.getFechaExpedicion())
                    .build());
        }
    }

    public void crearDirecciones(List<DireccionTerDTO> direcciones,TerceroEntity tercero,
                                 ContactoEntity contacto){
        if (direcciones != null){
            direcciones.forEach(d->{
                if(d.getTelefonos().size() > 0){
                    datosContactoAdapter.crearTelefonosAsociarNuevaDireccion(
                            modelMapper.map(d, DireccionTelefonosReqDTO.class),
                            tercero,
                            contacto
                    );
                }else{
                    datosContactoAdapter.crearDireccionAsociarTercero(
                            modelMapper.map(d, DireccionReqDTO.class),
                            tercero,
                            contacto
                    );
                }
            });
        }
    }

    public void crearTelefonos(List<TelefonoTerDTO> telefonos,TerceroEntity tercero
            ,ContactoEntity contacto){
        if (telefonos != null){
            telefonos.forEach(t->{
                datosContactoAdapter.crearTelefonoAsociarTercero(
                        modelMapper.map(t, TelefonoReqDTO.class),
                        tercero,contacto);
            });
        }
    }

    public void crearEmails(List<EmailTerDTO> emails,List<TerceroRolTipoTerEntity> terceroRoles,
                    ContactoEntity contacto){
        if (emails != null){
            if (contacto != null){
                emails.forEach(e->{
                    emailCreacionAdapter.crearEmailContacto(
                            EmailReqDTO.builder()
                                    .email(e.getEmail())
                                    .tipoCorreo(e.getTipoCorreo())
                                    .build(),
                            contacto

                    );
                });
            }else{
                emails.forEach(e->{
                    RolTipoTerceroEntity rol = rolAdapter.obtenerRolTipoTerceroOException(e.getRol());
                    //busco el tercero con el rol indicado
                    TerceroRolTipoTerEntity terceroRol = terceroRoles.stream()
                            .filter(tr->(tr.getRol().getId().equals(rol.getId())))
                            .findFirst()
                            .orElse(null);

                    if (terceroRol == null) throw new BadRequestCustom("El rol del correo "+e.getEmail()+" no esta relacionado con el tercero");

                    //crea el correo y lo une al tercero_rol_email
                    emailCreacionAdapter.crearEmailTercero(EmailReqDTO
                                    .builder()
                                    .email(e.getEmail())
                                    .tipoCorreo(e.getTipoCorreo())
                                    .build()
                            , terceroRol);
                });
            }
        }
    }
}
