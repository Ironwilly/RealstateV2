package edu.salesianos.triana.RealStateV2.repositorios;

import edu.salesianos.triana.RealStateV2.model.Interesa;
import edu.salesianos.triana.RealStateV2.model.InteresaPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteresaRepository extends JpaRepository<Interesa, InteresaPk> {
}
