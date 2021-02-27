package info.andchelp.fitwf.model;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {

    @Column(updatable = false, nullable = false, unique = true)
    String username;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false, length = 60)
    String password;

    @Builder.Default
    @Column(nullable = false)
    boolean emailVerified = false;

    @Builder.Default
    @Column(nullable = false)
    boolean enabled = true;

    @Singular
    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Column(nullable = false)
    Set<Role> authorities;

    @Override
    public Set<Role> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
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
}
