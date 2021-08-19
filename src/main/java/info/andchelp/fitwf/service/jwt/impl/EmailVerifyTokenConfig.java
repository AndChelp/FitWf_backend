package info.andchelp.fitwf.service.jwt.impl;

import info.andchelp.fitwf.service.jwt.JwtTokenConfig;
import info.andchelp.fitwf.service.jwt.TokenType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
@Getter
public class EmailVerifyTokenConfig implements JwtTokenConfig {

    @Getter
    private final TokenType tokenType = TokenType.EMAIL_VERIFY;

    @Value("${jwt.token.verify.email.duration}")
    private Long duration;
    @Value("${jwt.token.verify.email.chronoUnit}")
    private ChronoUnit chronoUnit;

}
