package info.andchelp.fitwf.error.exception;

import info.andchelp.fitwf.error.enums.ExceptionType;
import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException {
    final ExceptionType type;

    public AbstractException(ExceptionType type) {
        super(type.getDictionaryCode());
        this.type = type;
    }
}
