package edu.salesianos.triana.RealStateV2.dto.inmobiliaria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateInmobiliariaDto {

    private String nombre;
    private String email;
    private String telefono;

}
