package info.andchelp.fitwf.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private final ResponseType type;
    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private final Object result;

    private ResponseDto(ResponseType type, Object result) {
        this.type = type;
        this.result = result;
    }

    public static ResponseDto ofSuccess(Object value) {
        return new ResponseDto(ResponseType.SUCCESS, value);
    }

    public static ResponseDto ofError(String exceptionCode, Object details) {
        return new ResponseDto(ResponseType.ERROR, ExceptionDto.of(exceptionCode, details));
    }

    public enum ResponseType {
        SUCCESS,
        ERROR
    }
}
