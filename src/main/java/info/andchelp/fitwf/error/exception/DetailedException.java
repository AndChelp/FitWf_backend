package info.andchelp.fitwf.error.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class DetailedException extends AbstractException {
    private final Map<String, Object> details;

    public DetailedException(String exceptionCode, Map<String, Object> details) {
        super(exceptionCode);
        this.details = details;
    }
}
