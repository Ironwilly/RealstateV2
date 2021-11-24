package edu.salesianos.triana.RealStateV2.model;

import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Vivienda implements Serializable {


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String avatar;
    private String latlng;
    private String direccion;
    private String codigoPostal;



    private String poblacion;
    private String provincia;
    private String tipo;
    private Double precio;
    private int numHabitaciones;
    private double metrosCuadrados;
    private int numBanyos;
    private boolean tienePiscina;
    private boolean tieneAscensor;
    private boolean tieneGaraje;

    @ManyToOne (fetch = FetchType.LAZY)
    private Inmobiliaria inmobiliaria;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @Builder.Default
    @OneToMany(mappedBy = "vivienda",cascade = CascadeType.REMOVE)
    private List<Interesa> listInteresa = new ArrayList<>();



    public Vivienda(Long id, String titulo, String avatar, String codigoPostal, String provincia, String tipo, Double precio,
                    int numHabitaciones, double metrosCuadrados, int numBanyos, boolean tienePiscina,
                    boolean tieneAscensor, boolean tieneGaraje) {
        this.id = id;
        this.titulo = titulo;
        this.avatar = avatar;
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
        this.tipo = tipo;
        this.precio = precio;
        this.numHabitaciones = numHabitaciones;
        this.metrosCuadrados = metrosCuadrados;
        this.numBanyos = numBanyos;
        this.tienePiscina = tienePiscina;
        this.tieneAscensor = tieneAscensor;
        this.tieneGaraje = tieneGaraje;
    }

    //HELPERS INMOBILIARIA

    public void addInmobiliaria(Inmobiliaria i){
        this.inmobiliaria = i;
        i.getViviendas().add(this);
    }

    public void removeInmobiliaria(Inmobiliaria i){

        i.getViviendas().remove(this);
        this.inmobiliaria = null;
    }


}
