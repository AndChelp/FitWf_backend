package info.andchelp.fitwf.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionDto {
    private String exceptionCode;
    private Object details;

    public static ExceptionDto of(String exceptionCode, Object details) {
        return new ExceptionDto(exceptionCode, details);
    }
}
