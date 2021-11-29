package edu.salesianos.triana.RealStateV2.controller;

import edu.salesianos.triana.RealStateV2.dto.vivienda.*;
import edu.salesianos.triana.RealStateV2.model.Vivienda;
import edu.salesianos.triana.RealStateV2.pagination.PaginationUtilsLinks;
import edu.salesianos.triana.RealStateV2.repositorios.InmobiliariaRepository;
import edu.salesianos.triana.RealStateV2.repositorios.InteresaRepository;
import edu.salesianos.triana.RealStateV2.repositorios.ViviendaRepository;
import edu.salesianos.triana.RealStateV2.services.InmobiliariaService;
import edu.salesianos.triana.RealStateV2.services.ViviendaService;
import edu.salesianos.triana.RealStateV2.users.model.Roles;
import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import edu.salesianos.triana.RealStateV2.users.repositorios.UserEntityRepository;
import edu.salesianos.triana.RealStateV2.users.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/vivienda")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Vivienda",description = "Controlador para Viviendas")

public class ViviendaController {

    private final ViviendaService viviendaService;
    private final PaginationUtilsLinks paginationUtilsLinks;
    private final ListViviendaDtoConverter listViviendaDtoConverter;
    private final DetailDtoConverter detaildtoConverter;
    private final ViviendaRepository viviendaRepository;
    private final InteresaRepository interesaRepository;
    private final InmobiliariaRepository inmobiliariaRepository;
    private final UsuarioService usuarioService;
    private final InmobiliariaService inmobiliariaService;
    private final UserEntityRepository userEntityRepository;



    @Operation(summary = "Obtiene una lista de todas las viviendas y las filtra según varios criterios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de viviendas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la lista de viviendas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})
    })
    @GetMapping("/")
    public ResponseEntity<?> findAllWithCriteria(
            @RequestParam("tipo") Optional<String> tipo,
            @RequestParam("ciudad") Optional<String> ciudad,
            @RequestParam("codigoPostal") Optional<String> codigoPostal,
            @RequestParam("provincia") Optional<String> provincia,
            @RequestParam("numHabitaciones") Optional<Integer> numHabitaciones,
            @RequestParam("metrosCuadradosMin") Optional<Double> metrosCuadradosMin,
            @RequestParam("metrosCuadradosMax") Optional<Double> metrosCuadradosMax,
            @RequestParam("precioMin") Optional<Double> precioMin,
            @RequestParam("precioMax") Optional<Double> precioMax,
            @PageableDefault(size = 10, page = 0) Pageable pageable, HttpServletRequest request) {

        Page<Vivienda> result = viviendaService.findByArgs(tipo, ciudad, codigoPostal, provincia,
                numHabitaciones, metrosCuadradosMin, metrosCuadradosMax, precioMin, precioMax, pageable);

        if (result.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity
                    .ok()
                    .header("link", paginationUtilsLinks.createLinkHeader(result, uriBuilder))
                    .body(result.stream()
                            .map(listViviendaDtoConverter::viviendaToGetViviendaDto)
                            .collect(Collectors.toList()));
        }
    }



    @Operation(summary = "Optiene los detalles de la vivienda elegida por el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la vivienda correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<GetDetailViviendaDto> findOne(@PathVariable Long id) {
        return ResponseEntity
                .ok(detaildtoConverter.viviendaToGetViviendaDto(viviendaService.findById(id).get()));
    }


    @Operation(summary = "Edita una vivienda anteriormente creada, buscando por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado la vivienda",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha editado la vivienda",
                    content = @Content),
    })
    @PutMapping("{id}")
    public ResponseEntity<Vivienda> edit (@RequestBody Vivienda v, @PathVariable Long id, @AuthenticationPrincipal Usuario usuarioAuth) {


        if (!usuarioAuth.getRole().equals(Roles.ADMIN) && !viviendaService.findById(id).get().getUsuario().getId().equals(usuarioAuth.getId())) {
            return ResponseEntity.notFound().build();

        } else {
            return ResponseEntity.of(

                    viviendaService.findById(id).map(m -> {
                        m.setTitulo(v.getTitulo());
                        m.setDescripcion(v.getDescripcion());
                        m.setAvatar(v.getAvatar());
                        m.setCodigoPostal(v.getCodigoPostal());
                        m.setLatlng(v.getLatlng());
                        m.setMetrosCuadrados(v.getMetrosCuadrados());
                        m.setNumHabitaciones(v.getNumHabitaciones());
                        m.setPoblacion(v.getPoblacion());
                        m.setPrecio(v.getPrecio());
                        m.setProvincia(v.getProvincia());
                        m.setDireccion(v.getDireccion());
                        m.setTienePiscina(v.isTienePiscina());
                        m.setTieneAscensor(v.isTieneAscensor());
                        m.setTieneGaraje(v.isTieneGaraje());
                        viviendaService.save(m);

                        return m;
                    })
            );
        }

    }
    @Operation(summary = "Se crea nueva vivienda con propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado la nueva vivienda",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha creado la nueva vivienda",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<Vivienda> createVivienda(@RequestBody Vivienda vivienda, @AuthenticationPrincipal Usuario usuarioAuth) {

        if (vivienda.getTitulo().isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {


                vivienda.addPropietario(usuarioAuth);
            viviendaService.save(vivienda);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(vivienda);
        }
    }

    @Operation(summary = "Borra una vivienda")
    @ApiResponse(responseCode = "204",
            description = "La vivienda se ha borrado correctamente",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Vivienda.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Parameter(description = "ID de la vivienda a borrar")
                                    @PathVariable Long id){
        viviendaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Borrar inmobiliaria asociada a una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina la inmbiliaria asociada a la vivienda pero no se borra la inmobiliaria",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se a podido emcontrar la vivienda",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})})
    @DeleteMapping("/{id}/inmobiliaria")
    public ResponseEntity<?> deleteInmboiliariaAsociada (
            @Parameter(description = "ID de la vivienda a buscar")
            @PathVariable Long id){

        Optional <Vivienda> vivienda = viviendaService.findById(id);

        if (vivienda.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            vivienda.map(v -> {
                v.setInmobiliaria(null);
                viviendaService.save(v);
                return ResponseEntity.noContent().build();

            });

            return ResponseEntity.noContent().build();

        }

    }




    @GetMapping("/propietario")

    public ResponseEntity<List<GetListViviendaDto>> findAllViviendasPropietario(@AuthenticationPrincipal Usuario usuarioAuth){
        Optional<List<Vivienda>> viviendas = viviendaService.viviendasPro(usuarioAuth);

        if(viviendas.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(
                    viviendas.get().stream()
                            .map(listViviendaDtoConverter::viviendaToGetViviendaDto)
                            .collect(Collectors.toList())
            );
        }




    }









}
