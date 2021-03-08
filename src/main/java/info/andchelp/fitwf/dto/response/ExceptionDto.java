package info.andchelp.fitwf.dto.response;

import info.andchelp.fitwf.error.enums.ExceptionType;
import lombok.Builder;

@Builder
public class ExceptionDto {
    ExceptionType type;
    String message;
}
