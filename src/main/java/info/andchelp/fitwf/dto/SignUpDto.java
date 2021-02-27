package info.andchelp.fitwf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpDto {
    String email;
    String username;
    String password;
}
