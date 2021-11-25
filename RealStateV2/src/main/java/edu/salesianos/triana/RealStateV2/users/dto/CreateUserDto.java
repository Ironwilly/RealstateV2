package edu.salesianos.triana.RealStateV2.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String nombre;
    private String apellidos;
    private String avatar;
    private String email;
    private String password;
    private String password2;

}
