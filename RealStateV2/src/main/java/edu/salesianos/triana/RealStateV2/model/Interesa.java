package edu.salesianos.triana.RealStateV2.model;

import edu.salesianos.triana.RealStateV2.users.model.Usuario;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Interesa implements Serializable {

    @Builder.Default
    @EmbeddedId
    private InteresaPk id = new InteresaPk();

    @ManyToOne
    @MapsId("vivienda_id")
    @JoinColumn(name = "vivienda_id")
    private Vivienda vivienda;

    @ManyToOne
    @MapsId("usuario_id")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @CreatedDate
    private LocalDateTime createDate;
    @Lob
    private String mensaje;


    //HELPERS
    //
    //
    //

    public void addToVivienda (Vivienda v){
        this.vivienda = v;
        v.getListInteresa().add(this);
    }

    public void deleteFromVivienda(Vivienda v){

        v.getListInteresa().remove(this);
        this.vivienda = null;
    }

    public void addToUsuario(Usuario u){
        this.usuario = u;
        u.getListInteresa().add(this);
    }

    public void  deleteFromUsuario(Usuario u){
        u.getListInteresa().remove(this);
        this.usuario = null;
    }

    public void addToInteresado(Usuario u){
        this.usuario = u;
        if (u.getListInteresa() == null){
            u.setListInteresa(new ArrayList<>());
            u.getListInteresa().add(this);

        }
        u.getListInteresa().add(this);
    }

    public void addViviendaToUsuario(Vivienda v, Usuario u){
        addToVivienda(v);
        addToUsuario(u);
    }

    public void  deleteViviendaFromUsuario(Vivienda v,Usuario u){
        deleteFromVivienda(v);
        deleteFromUsuario(u);
    }

    public void deleteFromInteresado(Usuario u){
        u.getListInteresa().remove(this);
        this.usuario = null;
    }

}
