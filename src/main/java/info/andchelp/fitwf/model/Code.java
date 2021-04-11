package info.andchelp.fitwf.model;

import info.andchelp.fitwf.model.enums.CodeType;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "codes")
public class Code extends AbstractEntity {
    @ManyToOne
    User user;

    @Column(unique = true, nullable = false)
    UUID code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    CodeType type;

    @Column(nullable = false)
    Timestamp expires_at;
}
