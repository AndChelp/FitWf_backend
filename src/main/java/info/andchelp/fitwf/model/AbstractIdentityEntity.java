package info.andchelp.fitwf.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractIdentityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Timestamp createdAt = Timestamp.from(Instant.now());

}
