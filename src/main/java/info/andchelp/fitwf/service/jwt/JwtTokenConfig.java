package info.andchelp.fitwf.service.jwt;

import java.time.temporal.ChronoUnit;

public interface JwtTokenConfig {

    ChronoUnit getChronoUnit();

    Long getDuration();

    TokenType getTokenType();

}
