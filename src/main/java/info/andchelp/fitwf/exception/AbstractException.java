package info.andchelp.fitwf.exception;

import info.andchelp.fitwf.exception.enums.ExceptionType;
import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException {
    private final ExceptionType type;

    public AbstractException(ExceptionType type) {
        super(type.getDictionaryCode());
        this.type = type;
    }
}
