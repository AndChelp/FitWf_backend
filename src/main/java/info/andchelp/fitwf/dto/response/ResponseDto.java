package info.andchelp.fitwf.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    private final Object result;

    public static ResponseDto of(Object value) {
        return new ResponseDto(value);
    }

    private ResponseDto(Object result) {
        this.result = result;
    }
}
