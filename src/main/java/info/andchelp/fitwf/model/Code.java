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
    private User user;

    @Column(unique = true, nullable = false)
    private UUID code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CodeType type;

    @Column(nullable = false)
    private Timestamp expires_at;
}
