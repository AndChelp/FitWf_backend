package info.andchelp.fitwf.error.exception;

import info.andchelp.fitwf.error.enums.ExceptionCode;

import java.util.Map;

public class IllegalArgumentException extends DetailedException {
    private IllegalArgumentException(String exceptionCode, Map<String, Object> details) {
        super(exceptionCode, details);
    }

    public static IllegalArgumentException ofMailVerify(String username) {
        return new IllegalArgumentException(ExceptionCode.USER_IS_VERIFIED,
                Map.of("username", username));
    }

}
