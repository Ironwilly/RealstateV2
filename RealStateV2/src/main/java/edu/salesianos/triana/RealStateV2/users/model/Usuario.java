package edu.salesianos.triana.RealStateV2.users.model;


import edu.salesianos.triana.RealStateV2.model.Inmobiliaria;
import edu.salesianos.triana.RealStateV2.model.Interesa;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Parameter;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Data


public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )

    @Column(name = "id", updatable = false,nullable = false)
    private UUID id;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String nombre;

    private String apellidos;

    private String direccion;

    private String email;

    private String telefono;

    private String avatar;

    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Builder.Default
    @OneToMany(mappedBy = "vivienda")
    private List<Interesa> listInteresa = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createDateAuthority;

    @Builder.Default
    private LocalDateTime lastPasswordChangeAuthority = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "inmobiliaria_id")
    private Inmobiliaria inmobiliaria;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));

    }

    @Override
    public String getUsername(){
        return email;
    }



    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Usuario(String nombre, String apellidos, String direccion, String email, String telefono, String avatar) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.avatar = avatar;
    }

    public void addInmobiliaria(Inmobiliaria i) {
        this.inmobiliaria = i;
        i.getGestores().add(this);
    }

    public void removeInmobiliaria(Inmobiliaria i) {
        i.getGestores().remove(this);
        this.inmobiliaria = null;
    }


    public Object getRol() {
        return null;
    }

    public Optional<Object> getListaViviendas() {
        return null;
    }
}
