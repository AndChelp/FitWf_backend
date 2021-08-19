package info.andchelp.fitwf.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PairAuthTokens {
    private final String accessToken;
    private final String refreshToken;
}
