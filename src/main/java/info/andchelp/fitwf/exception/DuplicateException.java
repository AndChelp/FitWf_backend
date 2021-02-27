package info.andchelp.fitwf.exception;

import info.andchelp.fitwf.exception.enums.ExceptionType;

import java.util.Map;

public class DuplicateException extends DetailedException {

    public DuplicateException(ExceptionType type, Map<String, Object> values) {
        super(type, values);
    }

    public static DuplicateException ofEmailAndUsername(String email, String username) {
        return new DuplicateException(ExceptionType.EMAIL_AND_USERNAME_EXISTS,
                Map.of("email", email,
                        "username", username));
    }

    public static DuplicateException ofEmail(String email) {
        return new DuplicateException(ExceptionType.EMAIL_EXISTS,
                Map.of("email", email));
    }

    public static DuplicateException ofUsername(String username) {
        return new DuplicateException(ExceptionType.USERNAME_EXISTS,
                Map.of("username", username));
    }

    public static DuplicateException of(String name, Object value) {
        return new DuplicateException(ExceptionType.DUPLICATED_VALUE, Map.of(name, value));
    }
}
