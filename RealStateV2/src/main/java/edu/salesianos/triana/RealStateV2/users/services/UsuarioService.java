package edu.salesianos.triana.RealStateV2.users.services;


import edu.salesianos.triana.RealStateV2.model.Inmobiliaria;
import edu.salesianos.triana.RealStateV2.services.base.BaseService;
import edu.salesianos.triana.RealStateV2.users.dto.CreateUserDto;
import edu.salesianos.triana.RealStateV2.users.model.Roles;
import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import edu.salesianos.triana.RealStateV2.users.repositorios.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UsuarioService extends BaseService<Usuario, UUID, UserEntityRepository> implements UserDetailsService {

    private  final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));

    }

    public Optional<Usuario> loadUserById(UUID id) throws UsernameNotFoundException {
        return this.repositorio.findById(id);
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

    public Usuario savePropietario(CreateUserDto nuevoPropietario) {
        if (nuevoPropietario.getPassword().contentEquals(nuevoPropietario.getPassword2())) {
            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(nuevoPropietario.getPassword()))
                    .avatar(nuevoPropietario.getAvatar())
                    .nombre(nuevoPropietario.getNombre())
                    .apellidos(nuevoPropietario.getApellidos())
                    .email(nuevoPropietario.getEmail())
                    .role(Roles.PROPIERTARIO)
                    .build();
            return save(usuario);
        } else {
            return null;
        }
    }

    public Usuario saveGestor(CreateUserDto nuevoGestor) {
        if (nuevoGestor.getPassword().contentEquals(nuevoGestor.getPassword2())) {
            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(nuevoGestor.getPassword()))
                    .avatar(nuevoGestor.getAvatar())
                    .nombre(nuevoGestor.getNombre())
                    .apellidos(nuevoGestor.getApellidos())
                    .email(nuevoGestor.getEmail())
                    .role(Roles.GESTOR)
                    .build();
            return save(usuario);
        } else {
            return null;
        }
    }

    public Usuario saveGestorWithoutId(CreateUserDto nuevoGestor, Inmobiliaria inmobiliaria) {

        if (nuevoGestor.getPassword().contentEquals(nuevoGestor.getPassword2())) {
            Usuario user = Usuario.builder()
                    .password(passwordEncoder.encode(nuevoGestor.getPassword()))
                    .avatar(nuevoGestor.getAvatar())
                    .apellidos(nuevoGestor.getApellidos())
                    .nombre(nuevoGestor.getNombre())
                    .email(nuevoGestor.getEmail())
                    .role(Roles.GESTOR)
                    .inmobiliaria(null)
                    .build();

            user.addInmobiliaria(inmobiliaria);
            try{
                return save(user);
            }catch (DataIntegrityViolationException ex){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de ese usuario ya existe");
            }
        } else {
            return null;
        }
    }



}
