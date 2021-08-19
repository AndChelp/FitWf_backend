package info.andchelp.fitwf.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "revoked_refresh_token")
public class RevokedRefreshToken extends AbstractIdentityEntity {

    private UUID refreshJti;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
