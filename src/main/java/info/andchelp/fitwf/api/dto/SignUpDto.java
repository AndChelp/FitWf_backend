package info.andchelp.fitwf.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {
    private String email;
    private String username;
    private String password;
}
