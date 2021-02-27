package info.andchelp.fitwf.exception;

import info.andchelp.fitwf.exception.enums.ExceptionType;
import lombok.Getter;

import java.util.Map;

@Getter
public abstract class DetailedException extends AbstractException {
    final Map<String, Object> details;

    public DetailedException(ExceptionType type, Map<String, Object> details) {
        super(type);
        this.details = details;
    }
}
