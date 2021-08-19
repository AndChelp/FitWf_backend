package info.andchelp.fitwf.service.jwt;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TokenConfigFactory {
    private final List<JwtTokenConfig> tokens;

    public TokenConfigFactory(List<JwtTokenConfig> tokens) {
        this.tokens = tokens;
    }

    public JwtTokenConfig getByType(TokenType tokenType) {
        return tokens.stream()
                .filter(t -> Objects.equals(t.getTokenType(), tokenType))
                .findAny()
                .orElseThrow();
    }

}
