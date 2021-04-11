package info.andchelp.fitwf.security;

import lombok.Builder;
import lombok.Getter;

import java.security.Principal;

@Getter
@Builder
public class JwtUser implements Principal {
    long id;
    String name;
    String email;
    boolean emailVerified;

    @Override
    public String getName() {
        return name;
    }
}
