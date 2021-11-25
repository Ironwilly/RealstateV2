package edu.salesianos.triana.RealStateV2.users.services;


import edu.salesianos.triana.RealStateV2.services.base.BaseService;
import edu.salesianos.triana.RealStateV2.users.dto.CreateUserDto;
import edu.salesianos.triana.RealStateV2.users.model.Roles;
import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import edu.salesianos.triana.RealStateV2.users.repositorios.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UsuarioService extends BaseService<Usuario, UUID, UserEntityRepository> implements UserDetailsService {

    private  final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));

    }

    public Usuario save(CreateUserDto newUser) {
        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            Usuario userEntity = Usuario.builder()
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .avatar(newUser.getAvatar())
                    .nombre(newUser.getNombre())
                    .apellidos(newUser.getApellidos())
                    .email(newUser.getEmail())
                    .role(Roles.ADMIN)
                    .build();
            return save(userEntity);
        } else {
            return null;
        }
    }



}
