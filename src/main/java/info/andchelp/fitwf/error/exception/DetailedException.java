package info.andchelp.fitwf.error.exception;

import info.andchelp.fitwf.error.enums.ExceptionType;
import lombok.Getter;

import java.util.Map;

@Getter
public abstract class DetailedException extends AbstractException {
    private final Map<String, Object> details;

    public DetailedException(ExceptionType type, Map<String, Object> details) {
        super(type);
        this.details = details;
    }
}
