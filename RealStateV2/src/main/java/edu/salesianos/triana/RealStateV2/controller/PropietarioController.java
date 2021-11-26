package edu.salesianos.triana.RealStateV2.controller;


import edu.salesianos.triana.RealStateV2.dto.propietario.GetPropietarioViviendaDto;
import edu.salesianos.triana.RealStateV2.dto.propietario.PropietarioDtoConverter;
import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import edu.salesianos.triana.RealStateV2.users.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Propietario", description = "Controller de los propietarios")
@RequestMapping("/propietario")
public class PropietarioController {

    private final UsuarioService usuarioService;
    private final PropietarioDtoConverter propietarioDtoConverter;
    private Object RolUsuario;

    /*
    @Operation(summary = "Muestra un propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el propietario",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPropietarioViviendaDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado el propietario",
                    content = @Content),
    })
    @GetMapping("{id}")
    public ResponseEntity<List<GetPropietarioViviendaDto>> findOne(@PathVariable UUID id, @AuthenticationPrincipal Usuario usuarioAuth){
        Optional<Usuario> propietario = usuarioService.loadUserById(id);

        if(!usuarioService.loadUserById(id).isEmpty() && !usuarioAuth.getRol().equals(RolUsuario.ADMIN) && !propietario.get().getId().equals(usuarioAuth.getId())){
            return ResponseEntity.notFound().build();
        }
        else{
            List<GetPropietarioViviendaDto> propietarioDTOS= propietario.stream()
                    .map(propietarioDtoConverter::propietarioToGetPropietarioViviendaDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(propietarioDTOS);
        }
    }

    @Operation(summary = "Borra un Propietario creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el propietario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))})
    })


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id, @AuthenticationPrincipal Usuario usuarioAuth) {

        Optional<Usuario> propietario = usuarioService.loadUserById(id);

        if(!usuarioService.loadUserById(id).isEmpty() && !usuarioAuth.getRol().equals(RolUsuario.ADMIN) && !propietario.get().getId().equals(usuarioAuth.getId())){
            return ResponseEntity.status(403).build();
        }
        else {
            usuarioService.deleteById(id);

            return ResponseEntity.noContent().build();
        }


    }

     */


}
