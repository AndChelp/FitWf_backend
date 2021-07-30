package info.andchelp.fitwf.error.exception;

import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException {
    private final String exceptionCode;

    public AbstractException(String exceptionCode) {
        super(exceptionCode);
        this.exceptionCode = exceptionCode;
    }
}
