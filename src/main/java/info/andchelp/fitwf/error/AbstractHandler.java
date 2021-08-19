package info.andchelp.fitwf.error;

import info.andchelp.fitwf.dictionary.ExceptionCode;
import info.andchelp.fitwf.dictionary.MessageSourceUtil;
import info.andchelp.fitwf.dto.response.ResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public abstract class AbstractHandler extends ResponseEntityExceptionHandler {

    protected final MessageSourceUtil messageSource;

    protected AbstractHandler(MessageSourceUtil messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Object m;
        if (body != null) {
            m = body;
        } else if (ex.getMessage() != null) {
            m = ex.getMessage();
        } else {
            m = messageSource.getMessage(ExceptionCode.DEFAULT);
        }

        ResponseDto responseDto = ResponseDto.ofError(ExceptionCode.DEFAULT, m);
        return super.handleExceptionInternal(ex, responseDto, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, "can't read request", headers, status, request);
    }
}
