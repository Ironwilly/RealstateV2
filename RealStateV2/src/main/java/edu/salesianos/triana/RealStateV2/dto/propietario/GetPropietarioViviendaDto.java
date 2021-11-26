package edu.salesianos.triana.RealStateV2.dto.propietario;


import edu.salesianos.triana.RealStateV2.dto.vivienda.GetViviendaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPropietarioViviendaDto {

    private UUID id;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private String telefono;
    private List<GetViviendaDto> viviendas;

}
