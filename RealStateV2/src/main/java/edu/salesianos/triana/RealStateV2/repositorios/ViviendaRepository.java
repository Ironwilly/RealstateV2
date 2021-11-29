package edu.salesianos.triana.RealStateV2.repositorios;

import edu.salesianos.triana.RealStateV2.dto.vivienda.GetListViviendaDto;
import edu.salesianos.triana.RealStateV2.model.Vivienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ViviendaRepository extends JpaRepository<Vivienda, Long>, JpaSpecificationExecutor<Vivienda> {
    @Query("""
            select new edu.salesianos.triana.RealStateV2.dto.vivienda(
                          
                   v.avatar,
                   v.codigoPostal,
                   v.provincia,
                   v.tipo,
                   v.precio,
                   v.numHabitaciones,
                   v.metrosCuadrados,
                   v.numBanyos,
                   v.tienePiscina,
                   v.tieneAscensor,
                   v.tieneGaraje,
                   v.nombre_propietario,
                   v.avatar_propietario,
                   v.propietarioId,
            )
            from Vivienda v where  v.propietarioId = :id
            """)
    List<GetListViviendaDto> viviendasPropi(UUID id);
}



