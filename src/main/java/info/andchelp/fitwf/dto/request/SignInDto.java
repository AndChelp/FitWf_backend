package info.andchelp.fitwf.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInDto {
    private final String username;
    private final String password;
}
