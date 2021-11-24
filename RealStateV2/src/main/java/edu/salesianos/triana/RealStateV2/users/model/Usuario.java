package edu.salesianos.triana.RealStateV2.users.model;


import edu.salesianos.triana.RealStateV2.model.Interesa;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Data


public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));

    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public boolean isAccountNonExpire(){
        return true;
    }


}
