package info.andchelp.fitwf.service.jwt.impl;

import info.andchelp.fitwf.service.jwt.JwtTokenConfig;
import info.andchelp.fitwf.service.jwt.TokenType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
@Getter
public class RefreshTokenConfig implements JwtTokenConfig {

    @Getter
    private final TokenType tokenType = TokenType.REFRESH;

    @Value("${jwt.token.refresh.duration}")
    private Long duration;
    @Value("${jwt.token.refresh.chronoUnit}")
    private ChronoUnit chronoUnit;

}
