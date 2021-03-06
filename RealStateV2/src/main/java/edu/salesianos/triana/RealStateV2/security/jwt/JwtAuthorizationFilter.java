package edu.salesianos.triana.RealStateV2.security.jwt;

import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import edu.salesianos.triana.RealStateV2.users.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UsuarioService userService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Obtener el token de la petición (request)
        String token = getJwtFromRequest(request);

        // 2. Validar token
        try {
            if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {


                UUID userId = jwtProvider.getUserIdFromJwt(token);

                Optional<Usuario> userEntity = userService.findById(userId);

                if (userEntity.isPresent()) {
                    Usuario user = userEntity.get();
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    user.getRole(),
                                    user.getAuthorities()
                            );
                    authentication.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);


                }
            }

        } catch (Exception ex) {
            // Informar en el log
            log.info("No se ha podido establecer el contexto de seguridad (" + ex.getMessage() + ")");
        }

        filterChain.doFilter(request, response);
        // 2.1 Si es válido, autenticamos al usuario

        // 2.2 Si no es válido, lanzamos una excepcion



    }

    private String getJwtFromRequest(HttpServletRequest request) {
        // Authorization: Bearer eltoken.qiemas.megusta


        String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length());
        }
        return null;
    }


}
