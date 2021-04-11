package info.andchelp.fitwf.error.exception;

import info.andchelp.fitwf.error.enums.ExceptionType;

import java.util.Map;

public class IllegalArgumentException extends DetailedException {
    private IllegalArgumentException(ExceptionType type, Map<String, Object> details) {
        super(type, details);
    }

    public static IllegalArgumentException ofMailVerify(String username) {
        return new IllegalArgumentException(ExceptionType.USER_IS_VERIFIED,
                Map.of("username", username));
    }

}
