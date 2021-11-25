package edu.salesianos.triana.RealStateV2.users.dto;


import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto convertUserEntityToGetUserDto(Usuario user) {
        return GetUserDto.builder()
                .avatar(user.getAvatar())
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();


    }
}
