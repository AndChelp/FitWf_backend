package info.andchelp.fitwf.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class RegisterDto {
    @Pattern(regexp = "^.+@.+\\..+", message = "{invalid.email}")
    private final String email;
    @Pattern(regexp = "^[\\w.-]{3,30}", message = "{invalid.username}")
    private final String username;
    @Pattern(regexp = ".{6,30}", message = "{invalid.password}")
    private final String password;
}
