package info.andchelp.fitwf.error.exception;

import info.andchelp.fitwf.dictionary.ExceptionCode;

public class AccessDeniedException extends AbstractException {
    private AccessDeniedException(String exceptionCode) {
        super(exceptionCode);
    }

    public static AccessDeniedException ofUsernameOrPassword() {
        return new AccessDeniedException(ExceptionCode.INVALID_USERNAME_OR_PASSWORD);
    }

    public static AccessDeniedException ofToken() {
        return new AccessDeniedException(ExceptionCode.INVALID_TOKEN);
    }
}
