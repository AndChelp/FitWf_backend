package info.andchelp.fitwf.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User extends AbstractEnabledEntity implements Principal {

    @Column(unique = true, nullable = false)
    private String email;

    @Column(updatable = false, nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column
    private Timestamp emailVerifiedAt;

    public void verifyEmail() {
        this.emailVerified = true;
        this.emailVerifiedAt = Timestamp.from(Instant.now());
    }

    @Override
    public String getName() {
        return getUsername();
    }
}
