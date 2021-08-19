package info.andchelp.fitwf.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import info.andchelp.fitwf.dictionary.ClaimKey;
import lombok.experimental.Delegate;

import java.util.Date;
import java.util.UUID;

public class JwtWrapper implements Payload {

    @Delegate
    private final DecodedJWT decodedJWT;

    public JwtWrapper(DecodedJWT decodedJWT) {
        this.decodedJWT = decodedJWT;
    }

    public UUID getUUID() {
        return UUID.fromString(decodedJWT.getId());
    }

    public UUID getRefreshJti() {
        return decodedJWT.getClaim(ClaimKey.REFRESH_JTI).as(UUID.class);
    }

    public long getUserId() {
        return decodedJWT.getClaim(ClaimKey.USER_ID).asLong();
    }

    public Date getRevokeAfter() {
        return decodedJWT.getClaim(ClaimKey.REVOKE_AFTER).asDate();
    }

}
