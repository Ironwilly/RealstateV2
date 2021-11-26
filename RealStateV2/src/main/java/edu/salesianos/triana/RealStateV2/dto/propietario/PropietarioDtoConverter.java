package edu.salesianos.triana.RealStateV2.dto.propietario;

import edu.salesianos.triana.RealStateV2.dto.vivienda.GetViviendaDto;
import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class PropietarioDtoConverter {

    public Usuario createPropietarioDtoToPropietario(CreatePropietarioDto p){
        return new Usuario(
                p.getNombre(),
                p.getApellidos(),
                p.getDireccion(),
                p.getEmail(),
                p.getTelefono(),
                p.getAvatar()
        );
    }
    public GetPropietarioDto propietarioToGetPropietarioDto(Usuario p){
        return GetPropietarioDto
                .builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .apellidos(p.getApellidos())
                .direccion(p.getDireccion())
                .email(p.getEmail())
                .telefono(p.getTelefono())
                .avatar(p.getAvatar())
                .build();
    }

    public GetPropietarioViviendaDto propietarioToGetPropietarioViviendaDto (Usuario p) {

        return GetPropietarioViviendaDto
                .builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .apellidos(p.getApellidos())
                .direccion(p.getDireccion())
                .email(p.getEmail())
                .telefono(p.getTelefono())
                .viviendas(p.getListaViviendas().stream().map(v -> new GetViviendaDto(
                        v.getId(),
                        v.getTitulo()
                        ,v.getDescripcion()
                        , v.getAvatar(),
                        v.getProvincia(),
                        v.getPrecio()
                        ,v.getNumHabitaciones(),
                        v.getMetrosCuadrados()
                ))
                        .toList())
                .build();
    }


}
