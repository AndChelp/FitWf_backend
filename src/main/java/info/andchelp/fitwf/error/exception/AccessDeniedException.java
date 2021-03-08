package info.andchelp.fitwf.error.exception;

import info.andchelp.fitwf.error.enums.ExceptionType;

public class AccessDeniedException extends AbstractException {
    private AccessDeniedException(ExceptionType type) {
        super(type);
    }

    public static AccessDeniedException ofUsernameOrPassword() {
        return new AccessDeniedException(ExceptionType.INVALID_USERNAME_OR_PASSWORD);
    }
    public static AccessDeniedException ofToken() {
        return new AccessDeniedException(ExceptionType.INVALID_TOKEN);
    }
}
