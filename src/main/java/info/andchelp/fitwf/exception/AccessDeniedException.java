package info.andchelp.fitwf.exception;

import info.andchelp.fitwf.exception.enums.ExceptionType;

public class AccessDeniedException extends AbstractException {
    private AccessDeniedException(ExceptionType type) {
        super(type);
    }

    public static AccessDeniedException ofUsernameOrPassword() {
        return new AccessDeniedException(ExceptionType.INVALID_USERNAME_OR_PASSWORD);
    }
}
