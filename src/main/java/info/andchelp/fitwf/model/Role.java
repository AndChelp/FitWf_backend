package info.andchelp.fitwf.model;

import info.andchelp.fitwf.model.enums.RoleType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity implements GrantedAuthority {
    @Column(nullable = false, updatable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleType type;

    @Override
    public String getAuthority() {
        return type.name();
    }
}
