package info.andchelp.fitwf.security.jwt;

import info.andchelp.fitwf.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final User user;
    private final JwtWrapper decodedJWT;

    public JwtAuthenticationToken(User user, JwtWrapper decodedJWT) {
        super(Collections.emptyList());
        this.user = user;
        this.decodedJWT = decodedJWT;
    }

    @Override
    public JwtWrapper getCredentials() throws RuntimeException {
        return decodedJWT;
    }

    @Override
    public User getPrincipal() {
        return user;
    }
}
