package info.andchelp.fitwf.error;

import info.andchelp.fitwf.dto.response.ExceptionDto;
import info.andchelp.fitwf.error.enums.ExceptionType;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public abstract class AbstractHandler extends ResponseEntityExceptionHandler implements ErrorController {

    protected final MessageSource messageSource;

    protected AbstractHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage() != null
                ? ex.getMessage()
                : messageSource.getMessage(ExceptionType.DEFAULT.getDictionaryCode(), null, LocaleContextHolder.getLocale());
        ExceptionDto exceptionBody = ExceptionDto.of(ExceptionType.DEFAULT, message, ex.getClass().getSimpleName());
        return super.handleExceptionInternal(ex, exceptionBody, headers, status, request);
    }

}
