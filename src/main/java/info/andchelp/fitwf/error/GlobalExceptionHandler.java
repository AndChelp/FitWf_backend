package info.andchelp.fitwf.error;

import info.andchelp.fitwf.dto.response.ExceptionDto;
import info.andchelp.fitwf.dto.response.ResponseDto;
import info.andchelp.fitwf.error.exception.AbstractException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, ResponseDto.of(body), headers, status, request);
    }



    @ExceptionHandler({AbstractException.class})
    public ResponseEntity<Object> tes(AbstractException ex, WebRequest request) {
        ExceptionDto build = ExceptionDto.builder().type(ex.getType()).message(ex.getType().getDictionaryCode()).build();
        return handleExceptionInternal(ex, build, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Deprecated
    public String getErrorPath() {
        return null;
    }
}
//Exception t = (Exception)request.getAttribute("javax.servlet.error.exception",0);