package edu.salesianos.triana.RealStateV2.users.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private String avatar;
    private String nombre;
    private String apellidos;
    private String email;
    private String role;
}
