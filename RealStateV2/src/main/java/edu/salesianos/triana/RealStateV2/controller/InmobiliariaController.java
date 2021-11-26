package edu.salesianos.triana.RealStateV2.controller;


import edu.salesianos.triana.RealStateV2.dto.inmobiliaria.CreateInmobiliariaDto;
import edu.salesianos.triana.RealStateV2.dto.inmobiliaria.GetInmoViviDto;
import edu.salesianos.triana.RealStateV2.dto.inmobiliaria.GetInmobiliariaDto;
import edu.salesianos.triana.RealStateV2.dto.inmobiliaria.InmoDtoConverter;
import edu.salesianos.triana.RealStateV2.dto.vivienda.ListViviendaDtoConverter;
import edu.salesianos.triana.RealStateV2.model.Inmobiliaria;
import edu.salesianos.triana.RealStateV2.model.Vivienda;
import edu.salesianos.triana.RealStateV2.pagination.PaginationUtilsLinks;
import edu.salesianos.triana.RealStateV2.repositorios.InmobiliariaRepository;
import edu.salesianos.triana.RealStateV2.repositorios.InteresaRepository;
import edu.salesianos.triana.RealStateV2.repositorios.ViviendaRepository;
import edu.salesianos.triana.RealStateV2.services.InmobiliariaService;
import edu.salesianos.triana.RealStateV2.services.InteresaService;
import edu.salesianos.triana.RealStateV2.services.ViviendaService;
import edu.salesianos.triana.RealStateV2.users.repositorios.UserEntityRepository;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inmobiliaria")
@CrossOrigin (origins = "http://localhost:4200")
@Tag(name = "Inmobiliaria",description = "Controlador para Inmobiliarias")
public class InmobiliariaController {

    private final InmobiliariaService inmobiliariaService;
    private final PaginationUtilsLinks paginationUtilsLinks;
    private final InmobiliariaRepository inmobiliariaRepository;
    private final ViviendaRepository viviendaRepository;
    private final ViviendaService viviendaService;
    private final UserEntityRepository userEntityRepository;
    private final InteresaRepository interesaRepository;
    private final ListViviendaDtoConverter listViviendaDtoConverter;
    private final InteresaService interesaService;
    private final InmoDtoConverter inmoDtoConverter;


/*
    @Operation(summary = "Obtiene una lista de todas las Inmobiliarias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de Inmobiliarias",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado ninguna Inmobiliaria",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})})
    @GetMapping("/")

    public ResponseEntity<Page<GetInmobiliariaDto>> findAll(@PageableDefault(size = 10,page = 0) Pageable pageable,
                                                            HttpServletRequest request) {

        Page<Inmobiliaria> data = inmobiliariaService.findAll(pageable);

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Page<GetInmobiliariaDto> result = data.map(inmoDtoConverter::inmobiliariaToGetInmobiliariaDto);

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

            return ResponseEntity.ok().header("link", PaginationUtilsLinks.createLinkHeader(result, uriBuilder)).body(result);
        }
    }
*/
    @Operation(summary = "Muestra una inmobiliaria y sus viviendas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la inmobiliaria",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetInmoViviDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la inmobiliaria",
                    content = @Content),
    })
    @GetMapping("{id}")
    public ResponseEntity<List<GetInmoViviDto>> findOne (@PathVariable Long id) {

        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);

        if (inmobiliaria.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else {
            List<GetInmoViviDto> result =
                    inmobiliaria.stream()
                            .map(inmoDtoConverter::inmobiliariaToGetInmobiliariaViviendaDto)
                            .collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }

    }


    @Operation(summary = "Crea una nueva inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la inmobiliaria correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear la inmobiliaria",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })
    @PostMapping("/")
    public ResponseEntity<Inmobiliaria> add(@RequestBody Inmobiliaria nueva){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inmobiliariaService.save(nueva));
    }


    @Operation(summary = "Edita los atributos de una inmobiliaria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la inmobiliaria y se ha modificado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la inmobiliaria indicada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<Inmobiliaria> edit(@RequestBody Inmobiliaria modificada,
                                             @Parameter(description = "ID de la inmobiliaria a editar")
                                             @PathVariable Long id){
        return ResponseEntity.of(
                inmobiliariaService.findById(id).map(inmobiliaria -> {
                    inmobiliaria.setNombre(modificada.getNombre());
                    inmobiliaria.setEmail(modificada.getEmail());
                    inmobiliaria.setTelefono(modificada.getTelefono());
                    inmobiliariaService.save(inmobiliaria);
                    return inmobiliaria;
                })
        );
    }

    @Operation(summary = "Borra una inmobiliaria previamente creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),


            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la inmobiliaria indicada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})})

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        inmobiliariaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
