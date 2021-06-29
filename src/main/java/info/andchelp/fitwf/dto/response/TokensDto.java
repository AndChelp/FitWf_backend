package info.andchelp.fitwf.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TokensDto {
    private final String accessToken;
    private final UUID refreshToken;
}
