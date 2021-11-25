package edu.salesianos.triana.RealStateV2.users.controller;

import edu.salesianos.triana.RealStateV2.users.dto.CreateUserDto;
import edu.salesianos.triana.RealStateV2.users.dto.GetUserDto;
import edu.salesianos.triana.RealStateV2.users.dto.UserDtoConverter;
import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import edu.salesianos.triana.RealStateV2.users.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UsuarioService usuarioService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestBody CreateUserDto newUser) {
        Usuario saved = usuarioService.save(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));

    }
}
