package info.andchelp.fitwf.security;

import lombok.Builder;
import lombok.Getter;

import java.security.Principal;

@Getter
@Builder
public class JwtUser implements Principal {
    private final long id;
    private final String name;
    private final String email;
    private final boolean emailVerified;

    @Override
    public String getName() {
        return name;
    }
}
