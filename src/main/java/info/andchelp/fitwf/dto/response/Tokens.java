package info.andchelp.fitwf.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Tokens {
    String accessToken;
    UUID refreshToken;
}
