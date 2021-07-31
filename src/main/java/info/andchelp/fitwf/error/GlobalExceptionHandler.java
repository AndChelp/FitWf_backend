package info.andchelp.fitwf.error;

import info.andchelp.fitwf.dictionary.ExceptionCode;
import info.andchelp.fitwf.dictionary.MessageSourceUtil;
import info.andchelp.fitwf.dto.response.ResponseDto;
import info.andchelp.fitwf.error.exception.AbstractException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Controller
public class GlobalExceptionHandler extends AbstractHandler {

    protected GlobalExceptionHandler(MessageSourceUtil messageSource) {
        super(messageSource);
    }

    @RequestMapping("${server.error.path:${error.path:/error}}")
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> defaultExceptionHandler(Exception thr, WebRequest request) {
        // Exception t = (Exception) request.getAttribute("javax.servlet.error.exception", 0);
        return new ResponseEntity<>(messageSource.getMessage(ExceptionCode.DEFAULT), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AbstractException.class})
    public ResponseEntity<Object> abstractExceptionHandler(AbstractException ex) {
        return new ResponseEntity<>(
                ResponseDto.ofError(
                        ex.getExceptionCode(),
                        messageSource.getMessage(ex.getExceptionCode())
                ), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(ResponseDto.ofError(ExceptionCode.VALIDATION_ERROR, validationErrors), HttpStatus.BAD_REQUEST);
    }
}