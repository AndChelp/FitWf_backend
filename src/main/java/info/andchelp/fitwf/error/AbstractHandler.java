package info.andchelp.fitwf.error;

import info.andchelp.fitwf.dictionary.ExceptionCode;
import info.andchelp.fitwf.dictionary.MessageSourceUtil;
import info.andchelp.fitwf.dto.response.ResponseDto;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public abstract class AbstractHandler extends ResponseEntityExceptionHandler implements ErrorController {

    protected final MessageSourceUtil messageSource;

    protected AbstractHandler(MessageSourceUtil messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage() != null
                ? ex.getMessage()
                : messageSource.getMessage(ExceptionCode.DEFAULT);

        ResponseDto responseDto = ResponseDto.ofError(ExceptionCode.DEFAULT, message);
        return super.handleExceptionInternal(ex, responseDto, headers, status, request);
    }

}
