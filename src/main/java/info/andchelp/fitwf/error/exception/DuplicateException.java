package info.andchelp.fitwf.error.exception;

import info.andchelp.fitwf.dictionary.ExceptionCode;

import java.util.Map;

public class DuplicateException extends DetailedException {

    private DuplicateException(String exceptionCode, Map<String, Object> details) {
        super(exceptionCode, details);
    }

    public static DuplicateException ofEmailAndUsername(String email, String username) {
        return new DuplicateException(ExceptionCode.EMAIL_AND_USERNAME_EXISTS,
                Map.of("email", email,
                        "username", username));
    }

    public static DuplicateException ofEmail(String email) {
        return new DuplicateException(ExceptionCode.EMAIL_EXISTS,
                Map.of("email", email));
    }

    public static DuplicateException ofUsername(String username) {
        return new DuplicateException(ExceptionCode.USERNAME_EXISTS,
                Map.of("username", username));
    }

    public static DuplicateException of(String name, Object value) {
        return new DuplicateException(ExceptionCode.DUPLICATED_VALUE, Map.of(name, value));
    }
}
