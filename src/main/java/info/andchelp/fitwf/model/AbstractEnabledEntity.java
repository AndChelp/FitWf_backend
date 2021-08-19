package info.andchelp.fitwf.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@MappedSuperclass
public abstract class AbstractEnabledEntity extends AbstractIdentityEntity {

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private Timestamp enabledAt = Timestamp.from(Instant.now());

}
