package info.andchelp.fitwf.dto.response;

import info.andchelp.fitwf.error.enums.ExceptionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionDto {
    private ExceptionType type;
    private Object details;
    private String klass;

    public static ExceptionDto of(ExceptionType type, Object details) {
        return of(type, details, null);
    }

    public static ExceptionDto of(ExceptionType type, Object details, String klass) {
        return new ExceptionDto(type, details, klass);
    }

}
