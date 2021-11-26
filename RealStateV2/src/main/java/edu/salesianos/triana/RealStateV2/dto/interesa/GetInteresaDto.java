package edu.salesianos.triana.RealStateV2.dto.interesa;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetInteresaDto {
    private String mensaje;
    private LocalDateTime createDate;
    private String tituloVivienda;
    private String provinciaVivienda;
    private double precioVivienda;
    private String descripcionVivienda;
    private String avatarVivienda;
}
