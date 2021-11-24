package edu.salesianos.triana.RealStateV2.users.services;


import edu.salesianos.triana.RealStateV2.services.base.BaseService;
import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import edu.salesianos.triana.RealStateV2.users.repositorios.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService extends BaseService<Usuario, Long, UserEntityRepository> implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));

    }

}
