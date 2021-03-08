package info.andchelp.fitwf.error.exception;

import info.andchelp.fitwf.error.enums.ExceptionType;

public class NotFoundException extends AbstractException {
    private NotFoundException(ExceptionType type) {
        super(type);
    }

    public static NotFoundException ofUser() {
        return new NotFoundException(ExceptionType.USER_NOT_FOUND);
    }

    public static NotFoundException ofCode() {
        return new NotFoundException(ExceptionType.CODE_NOT_FOUND);
    }
}
