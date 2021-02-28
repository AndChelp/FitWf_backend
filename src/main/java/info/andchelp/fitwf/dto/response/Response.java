package info.andchelp.fitwf.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

@Getter
@Builder
public class Response {
    @Builder.Default
    HttpStatus status = HttpStatus.OK;

    @Builder.Default
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    Object result;

    public static ResponseEntity<Response> of(Object value) {
        return ResponseEntity.ok(Response.builder().result(value).build());
    }

}
