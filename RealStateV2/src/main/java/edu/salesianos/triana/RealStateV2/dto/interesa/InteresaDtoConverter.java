package edu.salesianos.triana.RealStateV2.dto.interesa;
import edu.salesianos.triana.RealStateV2.dto.vivienda.GetViviendaInteresaDto;
import edu.salesianos.triana.RealStateV2.model.Interesa;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class InteresaDtoConverter {

    public Interesa createInteresaDto (GetViviendaInteresaDto getViviendaInteresaDto) {
        return Interesa.builder()
                .createDate(LocalDateTime.now())
                .mensaje(getViviendaInteresaDto.getMensaje())
                .build();
    }

    public Interesa getInteresaDto (GetInteresaDto interesa) {
        return Interesa.builder()
                .mensaje(interesa.getMensaje())
                .createDate(LocalDateTime.now())
                .build();
    }

}
