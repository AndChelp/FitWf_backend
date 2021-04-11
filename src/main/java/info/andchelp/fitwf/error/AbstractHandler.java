package info.andchelp.fitwf.error;

import info.andchelp.fitwf.dto.response.ExceptionDto;
import info.andchelp.fitwf.error.enums.ExceptionType;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public abstract class AbstractHandler extends ResponseEntityExceptionHandler implements ErrorController {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage() != null
                ? ex.getMessage()
                : ExceptionType.DEFAULT.translateException();
        ExceptionDto exceptionBody = ExceptionDto.of(ExceptionType.DEFAULT, message, ex.getClass().getSimpleName());
        return super.handleExceptionInternal(ex, exceptionBody, headers, status, request);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
