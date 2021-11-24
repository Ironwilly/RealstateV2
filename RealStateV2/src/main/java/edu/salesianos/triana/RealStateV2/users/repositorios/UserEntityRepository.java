package edu.salesianos.triana.RealStateV2.users.repositorios;


import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface UserEntityRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findFirstByEmail(String email);



}
