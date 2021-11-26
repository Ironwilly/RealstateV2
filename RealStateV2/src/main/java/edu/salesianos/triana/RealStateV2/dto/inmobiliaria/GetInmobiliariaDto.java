package edu.salesianos.triana.RealStateV2.dto.inmobiliaria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInmobiliariaDto {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;



}
