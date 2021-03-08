package info.andchelp.fitwf.error.exception;

import info.andchelp.fitwf.error.enums.ExceptionType;

import java.util.Map;

public class DuplicateException extends DetailedException {

    private DuplicateException(ExceptionType type, Map<String, Object> details) {
        super(type, details);
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
