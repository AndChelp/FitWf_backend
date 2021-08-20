package info.andchelp.fitwf.error.exception;

import info.andchelp.fitwf.dictionary.ExceptionCode;

public class NotFoundException extends AbstractException {
    private NotFoundException(String exceptionCode) {
        super(exceptionCode);
    }

    public static NotFoundException ofUser() {
        return new NotFoundException(ExceptionCode.USER_NOT_FOUND);
    }

}
