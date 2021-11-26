package edu.salesianos.triana.RealStateV2.dto.vivienda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetViviendaDto {

    private Long id;
    private String titulo;
    private String descripcion;
    private String avatar;
    private String provincia;
    private double precio;
    private int numHabitaciones;
    private double metrosCuadrados;
}
