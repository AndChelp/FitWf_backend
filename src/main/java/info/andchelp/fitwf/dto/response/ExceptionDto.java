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
    ExceptionType type;
    String message;
    String klass;

    public static ExceptionDto of(ExceptionType type) {
        return of(type, type.translateException());
    }

    public static ExceptionDto of(ExceptionType type, String message) {
        return of(type, message, null);
    }

    public static ExceptionDto of(ExceptionType type, String message, String klass) {
        return new ExceptionDto(type, message, klass);
    }

}
