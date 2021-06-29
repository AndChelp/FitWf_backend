package info.andchelp.fitwf.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final JwtUser jwtUser;

    public JwtAuthenticationToken(DecodedJWT decodedJWT) {
        super(decodedJWT.getClaim("authorities").asList(SimpleGrantedAuthority.class));
        this.jwtUser = JwtUser.builder()
                .id(decodedJWT.getClaim("userId").asLong())
                .name(decodedJWT.getClaim("sub").asString())
                .email(decodedJWT.getClaim("email").asString())
                .emailVerified(decodedJWT.getClaim("emailVerified").asBoolean())
                .build();
    }

    @Deprecated
    @Override
    public Object getCredentials() throws RuntimeException {
        throw new RuntimeException();
    }

    @Override
    public JwtUser getPrincipal() {
        return jwtUser;
    }
}
