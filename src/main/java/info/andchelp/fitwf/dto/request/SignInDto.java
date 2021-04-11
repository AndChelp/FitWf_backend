package info.andchelp.fitwf.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInDto {
    String username;
    String password;
}
