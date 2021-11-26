package edu.salesianos.triana.RealStateV2.dto.inmobiliaria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class GetInmoViviDto {


    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private List<String> viviendas;

}
