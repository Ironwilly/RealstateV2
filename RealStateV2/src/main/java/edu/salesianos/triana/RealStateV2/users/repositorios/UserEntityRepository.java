package edu.salesianos.triana.RealStateV2.users.repositorios;


import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;


public interface UserEntityRepository extends JpaRepository<Usuario, UUID>{

    Optional<Usuario> findFirstByEmail(String email);



}
