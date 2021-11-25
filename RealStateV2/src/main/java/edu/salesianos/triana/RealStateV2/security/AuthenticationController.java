package edu.salesianos.triana.RealStateV2.security;

import edu.salesianos.triana.RealStateV2.security.dto.JwtUserResponse;
import edu.salesianos.triana.RealStateV2.security.dto.LoginDto;
import edu.salesianos.triana.RealStateV2.security.jwt.JwtProvider;
import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Devolver una respuesta adecuada
        // que incluya el token del usuario.
        String jwt = jwtProvider.generateToken(authentication);


        Usuario user = (Usuario) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertUserToJwtUserResponse(user, jwt));

    }

    @GetMapping("/me")
    public ResponseEntity<?> quienSoyYo(@AuthenticationPrincipal Usuario user){
        return ResponseEntity.ok(convertUserToJwtUserResponse(user, null));
    }


    private JwtUserResponse convertUserToJwtUserResponse(Usuario user, String jwt) {
        return JwtUserResponse.builder()
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .role(user.getRole().name())
                .token(jwt)
                .build();
    }

}
