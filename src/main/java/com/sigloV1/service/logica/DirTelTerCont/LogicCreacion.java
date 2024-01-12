package com.sigloV1.service.logica.DirTelTerCont;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerConRepository;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerRepository;
import com.sigloV1.dao.repositories.contacto.ContactoRepository;
import com.sigloV1.service.adapters.DatosContactoAdapter;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.impl.DirTelTerCon.returnMethods.ReturnCustomDireccion;
import com.sigloV1.service.impl.DirTelTerCon.returnMethods.ReturnCustomTelefono;
import com.sigloV1.service.interfaces.direccionesTelefonos.IDireccionService;
import com.sigloV1.service.interfaces.direccionesTelefonos.ITelefonoService;
import com.sigloV1.service.logica.DirTelTerCont.Params.RelacionarALL;
import com.sigloV1.service.logica.DirTelTerCont.Params.TelefonoParams;
import com.sigloV1.service.logica.contacto.MetodosContacto;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionIdTelefonosReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.TelefonoReqDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionTelefonosResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.TelefonoResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


//TODO ----> las direcciones y telefonos usados en el rol contacto solo se usaran hay
@Component
public class LogicCreacion implements DatosContactoAdapter {

    @Autowired
    private DirTelTerRepository dirTelTerRepository;

    @Autowired
    private IDireccionService direccionService;

    @Autowired
    private ITelefonoService telefonoService;

    @Autowired
    private TerceroAdapter terceroAdapter;

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private MetodosContacto metodosContacto;

    @Autowired
    private DirTelTerConRepository relacionesContactoRep;


    public <T> void asociarDatosConContacto(DirTelTerEntity relacion, T contactoParam,String nombreDireccion) {

        ContactoEntity contacto = contactoParam instanceof ContactoEntity ? (ContactoEntity) contactoParam :
                metodosContacto.obtenerContactoOException((Long) contactoParam);

        relacionesContactoRep
                .findByDireccionTelefonoAndContacto(relacion, contacto)
                .orElseGet(() -> (
                        relacionesContactoRep.save(
                                DirTelTerContEntity.builder()
                                        .direccionTelefono(relacion)
                                        .estadoDireccion(relacion.getDireccion() != null)
                                        .estadoTelefono(relacion.getTelefono() != null)
                                        .nombreDireccion(nombreDireccion)
                                        .contacto(contacto)
                                        .build()
                        )
                ));
    }

    //si es de contacto el nombre va en contacto
    //el tercero sera el contacto en si, es decir , el tercero en su rol como contacto
    //el contacto sla relacion entre este tercero contacto y su tercero dueño
    public void crearDireccionAsociarTercero(DireccionReqDTO data,TerceroEntity terceroInstance) {
        boolean isContacto = data.getContactoId() != null;
        ReturnCustomDireccion direccionNueva = direccionService.crearDireccion(data);
        TerceroEntity terceroEntity = terceroInstance == null ? terceroAdapter.obtenerTerceroOException(data.getTerceroId())
                : terceroInstance;


        if (isContacto) {
            ContactoEntity contacto = metodosContacto
                    .obtenerContactoOException(data.getContactoId());

            List<DirTelTerEntity> relacionesDelContacto = dirTelTerRepository
                    .relacionesDirTerComoContacto(terceroEntity, direccionNueva.getDireccion());

            if (!relacionesDelContacto.isEmpty()) {
                relacionesDelContacto.forEach(r -> {
                    if (r.getTelefono() != null) {
                        Optional<DirTelTerContEntity> relacion = relacionesContactoRep
                                .findByDireccionTelefonoAndContacto(r, contacto);
                        if (relacion.isPresent()) {
                            throw new BadRequestCustom("La direccion ya se encuentra asociada con el contacto");
                        }
                    }
                });
            }

            DirTelTerEntity relacion = dirTelTerRepository
                    .findByTerceroAndTelefonoAndDireccionAndUsadaEnContactoAndExtension(
                            terceroEntity, null, direccionNueva.getDireccion(), true,null);

            asociarDatosConContacto(Objects.requireNonNullElseGet(relacion, () -> dirTelTerRepository.save(
                    DirTelTerEntity.builder()
                            .direccion(direccionNueva.getDireccion())
                            .tercero(terceroEntity)
                            .usadaEnContacto(data.getContactoId() != null)
                            .build()
            )), contacto, data.getNombreDireccion());
        } else {
            List<DirTelTerEntity> relaciones = dirTelTerRepository.findByTerceroAndDireccionAndUsadaEnContacto(
                    terceroEntity, direccionNueva.getDireccion(), false);

            if (!relaciones.isEmpty()) {
                throw new BadRequestCustom("La relacion entre la direccion y el tercero ya existe");
            } else {
                dirTelTerRepository.save(
                        DirTelTerEntity.builder()
                                .direccion(direccionNueva.getDireccion())
                                .tercero(terceroEntity)
                                .nombreDireccion(data.getNombreDireccion())
                                .estadoDireccion(true)
                                .usadaEnContacto(data.getContactoId() != null)
                                .build()
                );
            }
        }
    }

    public void crearTelefonoAsociarTercero(TelefonoReqDTO data, TerceroEntity terceroInstance) {
        if(data.getTipoTelefono() == ETipoTelefono.FIJO) throw new BadRequestCustom("Los telefonos fijos deben estar asociados a una direccion");

        boolean isContacto = data.getContactoId() != null;
        ReturnCustomTelefono telefonoNuevo = telefonoService.crearTelefono(data);
        TerceroEntity terceroEntity = terceroInstance == null ? terceroAdapter.obtenerTerceroOException(data.getTerceroId())
                : terceroInstance;

        if (isContacto) {
            ContactoEntity contacto = metodosContacto
                    .obtenerContactoOException(data.getContactoId());

            List<DirTelTerEntity> relacionesDelContacto = dirTelTerRepository
                    .relacionesTelExtTerComoContacto(terceroEntity, telefonoNuevo.getTelefono(),data.getExtension());

            if (!relacionesDelContacto.isEmpty()) {
                relacionesDelContacto.forEach(r -> {
                    if (r.getDireccion() != null) {
                        Optional<DirTelTerContEntity> relacion = relacionesContactoRep
                                .findByDireccionTelefonoAndContacto(r, contacto);
                        if (relacion.isPresent()) {
                            throw new BadRequestCustom("El telefono ya se encuentra asociado con el contacto");
                        }
                    }
                });
            }

            DirTelTerEntity relacion = dirTelTerRepository
                    .findByTerceroAndTelefonoAndDireccionAndUsadaEnContactoAndExtension(
                            terceroEntity, telefonoNuevo.getTelefono(), null, true,data.getExtension());

            asociarDatosConContacto(Objects.requireNonNullElseGet(relacion, () -> dirTelTerRepository.save(
                    DirTelTerEntity.builder()
                            .telefono(telefonoNuevo.getTelefono())
                            .tercero(terceroEntity)
                            .extension(data.getExtension())
                            .usadaEnContacto(data.getContactoId() != null)
                            .build()
            )), contacto,null);

        } else {
            DirTelTerEntity relacion = dirTelTerRepository
                    .findByTerceroAndTelefonoAndUsadaEnContactoAndExtension(terceroEntity,
                            telefonoNuevo.getTelefono(), false, data.getExtension());

            if (relacion != null) {
                throw new BadRequestCustom("La relacion entre el telefono y el tercero ya existe");
            } else {
                dirTelTerRepository.save(
                        DirTelTerEntity.builder()
                                .telefono(telefonoNuevo.getTelefono())
                                .tercero(terceroEntity)
                                .extension(data.getExtension())
                                .estadoTelefono(true)
                                .usadaEnContacto(data.getContactoId() != null)
                                .build()
                );
            }
        }
    }

    public void crearTelefonosAsociarNuevaDireccion(DireccionTelefonosReqDTO dataDireccion,TerceroEntity terceroInstance) {

        TerceroEntity tercero = terceroInstance == null ? terceroAdapter.obtenerTerceroOException(dataDireccion.getTerceroId())
                :terceroInstance;

        ReturnCustomDireccion direccion = direccionService.crearDireccion(DireccionReqDTO
                .builder()
                .direccion(dataDireccion.getDireccion())
                .codigoPostal(dataDireccion.getCodigoPostal())
                .ciudad(dataDireccion.getCiudad())
                .build());

        List<TelefonoParams> telefonos = dataDireccion.getTelefonos().stream().map(t ->
                TelefonoParams.builder()
                        .telefono(telefonoService.crearTelefono(t).getTelefono())
                        .extension(t.getExtension())
                        .build()
        ).toList();

        relacionarDireccionAndTelefonosAndTercero(RelacionarALL.builder()
                .direccion(direccion.getDireccion())
                .telefonos(telefonos)
                .usadaComoContacto(dataDireccion.getContactoId())
                .tercero(tercero)
                .nombreDireccion(dataDireccion.getNombreDireccion())
                .build());
    }

    public void crearTelefonosAsociarDireccionExistente(DireccionIdTelefonosReqDTO data,TerceroEntity terceroInstance) {
        TerceroEntity tercero = terceroInstance == null ? terceroAdapter
                .obtenerTerceroOException(data.getIdTercero())
                :terceroInstance;

        DireccionEntity direccion = direccionService
                .obtenerDireccionOException(data.getDireccionId());

        List<TelefonoParams> telefonos = data.getTelefonos().stream().map(t ->
                TelefonoParams.builder()
                        .telefono(telefonoService.crearTelefono(t).getTelefono())
                        .extension(t.getExtension())
                        .build()
        ).toList();

        relacionarDireccionAndTelefonosAndTercero(RelacionarALL.builder()
                .direccion(direccion)
                .telefonos(telefonos)
                .usadaComoContacto(data.getContactoId())
                .nombreDireccion(data.getNombreDireccion())
                .tercero(tercero)
                .build());
    }



    public void relacionarDireccionAndTelefonosAndTercero(RelacionarALL data) {

        Boolean isContacto = data.getUsadaComoContacto() != null;

        List<DirTelTerEntity> relaciones = data.getTelefonos().stream()
                .map(t -> {
                    DirTelTerEntity relacionPreviaDireccion = dirTelTerRepository
                            .findByTerceroAndTelefonoAndDireccionAndUsadaEnContactoAndExtension(
                                    data.getTercero(), t.getTelefono(), data.getDireccion(), isContacto,t.getExtension()
                            );

                    if (relacionPreviaDireccion != null) {
                        return relacionPreviaDireccion;
                    }


                    DirTelTerEntity relacionPreviaTercero = t.getTelefono().getTipoTelefono() == ETipoTelefono.MOVIL ? dirTelTerRepository
                            .findByTerceroAndTelefonoAndDireccionAndUsadaEnContactoAndExtension(
                                    data.getTercero(), t.getTelefono(), null, isContacto,t.getExtension()
                            ) : null;

                    if (relacionPreviaTercero != null && !isContacto) {
                        relacionPreviaTercero.setDireccion(data.getDireccion());
                        relacionPreviaTercero.setEstadoDireccion(true);
                        relacionPreviaDireccion.setNombreDireccion(data.getNombreDireccion());
                        return dirTelTerRepository.save(relacionPreviaTercero);
                    } else {
                        return dirTelTerRepository.save(
                                DirTelTerEntity.builder()
                                        .tercero(data.getTercero())
                                        .telefono(t.getTelefono())
                                        .direccion(data.getDireccion())
                                        .estadoDireccion(isContacto ? null:true )
                                        .nombreDireccion(isContacto ? null : data.getNombreDireccion())
                                        .estadoTelefono(isContacto ? null: true)
                                        .extension(t.getExtension())
                                        .usadaEnContacto(isContacto)
                                        .build()
                        );
                    }
                }).toList();

        if (isContacto) {
            ContactoEntity contacto = metodosContacto.obtenerContactoOException(data.getUsadaComoContacto());

            relaciones.forEach(r -> {
                DirTelTerEntity relPrevTelefonoTer = r.getTelefono().getTipoTelefono() == ETipoTelefono.MOVIL ? dirTelTerRepository
                        .findByTerceroAndTelefonoAndDireccionAndUsadaEnContactoAndExtension(
                        data.getTercero(),
                        r.getTelefono(),
                        null,
                        true,
                                r.getExtension()
                ):null;

                if (relPrevTelefonoTer != null) {
                    Optional<DirTelTerContEntity> relacionTelConThisCont = relacionesContactoRep
                            .findByDireccionTelefonoAndContacto(relPrevTelefonoTer, contacto);

                    if (relacionTelConThisCont.isPresent()) {
                        List<DirTelTerContEntity> contactosUsandoRelacion = relacionesContactoRep
                                .findByDireccionTelefono(relPrevTelefonoTer);

                        relacionesContactoRep.delete(relacionTelConThisCont.get());
                        if (contactosUsandoRelacion.size() == 1) {
                            dirTelTerRepository.delete(relPrevTelefonoTer);
                        }
                    }
                }

                asociarDatosConContacto(r, contacto, data.getNombreDireccion());
            });
        }

        DirTelTerEntity relacionDireccionTer = dirTelTerRepository
                .findByTerceroAndTelefonoAndDireccionAndUsadaEnContactoAndExtension(
                        data.getTercero(), null, data.getDireccion()
                                , isContacto,null);


        if (relacionDireccionTer != null) {
            if (isContacto) {
                ContactoEntity contacto = metodosContacto.obtenerContactoOException(data.getUsadaComoContacto());

                Optional<DirTelTerContEntity> relacionDirConThisCont = relacionesContactoRep
                        .findByDireccionTelefonoAndContacto(relacionDireccionTer, contacto);

                if (relacionDirConThisCont.isPresent()) {
                    List<DirTelTerContEntity> contUsandoRelacion = relacionesContactoRep
                            .findByDireccionTelefono(relacionDireccionTer);
                    relacionesContactoRep.delete(relacionDirConThisCont.get());
                    if (contUsandoRelacion.size() == 1) {
                        dirTelTerRepository.delete(relacionDireccionTer);
                    }
                }
            } else {
                dirTelTerRepository.delete(relacionDireccionTer);
            }
        }

    }

}
