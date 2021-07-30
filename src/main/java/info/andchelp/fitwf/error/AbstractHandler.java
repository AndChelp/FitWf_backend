package info.andchelp.fitwf.error;

import info.andchelp.fitwf.dto.response.ResponseDto;
import info.andchelp.fitwf.error.enums.ExceptionCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public abstract class AbstractHandler extends ResponseEntityExceptionHandler implements ErrorController {

    private final MessageSource messageSource;

    protected AbstractHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage() != null
                ? ex.getMessage()
                : localizedMessageFor(ExceptionCode.DEFAULT);

        ResponseDto responseDto = ResponseDto.ofError(ExceptionCode.DEFAULT, message);
        return super.handleExceptionInternal(ex, responseDto, headers, status, request);
    }

    protected String localizedMessageFor(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    protected String localizedMessageFor(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
