package info.andchelp.fitwf.dto.request;

import info.andchelp.fitwf.error.enums.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Pattern;


@Getter
@AllArgsConstructor
public class RegisterDto {
    @Pattern(regexp = "^.+@.+\\..+", message = ExceptionCode.INVALID_EMAIL)
    private final String email;
    @Pattern(regexp = "^[\\w.-]{3,30}", message = ExceptionCode.INVALID_USERNAME)
    private final String username;
    @Pattern(regexp = ".{6,30}", message = ExceptionCode.INVALID_PASSWORD)
    private final String password;
}
