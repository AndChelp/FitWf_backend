package info.andchelp.fitwf.error;

import com.auth0.jwt.exceptions.JWTVerificationException;
import info.andchelp.fitwf.dictionary.ExceptionCode;
import info.andchelp.fitwf.dictionary.MessageSourceUtil;
import info.andchelp.fitwf.dto.response.ResponseDto;
import info.andchelp.fitwf.error.exception.AbstractException;
import info.andchelp.fitwf.error.exception.AccessDeniedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Controller
public class GlobalExceptionHandler extends AbstractHandler {
    private final HandlerExceptionResolver handlerExceptionResolver;

    protected GlobalExceptionHandler(MessageSourceUtil messageSource, HandlerExceptionResolver handlerExceptionResolver) {
        super(messageSource);
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @RequestMapping("${server.error.path:${error.path:/error}}")
    public void defaultExceptionHandler(HttpServletRequest request, HttpServletResponse response) {
        handlerExceptionResolver.resolveException(request, response, null, (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDto> defaultExceptionHandler() {
        return ResponseDto.ofError(
                ExceptionCode.DEFAULT,
                messageSource.getMessage(ExceptionCode.DEFAULT),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AbstractException.class})
    public ResponseEntity<ResponseDto> abstractExceptionHandler(AbstractException ex) {
        return ResponseDto.ofError(
                ex.getExceptionCode(),
                messageSource.getMessage(ex.getExceptionCode()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ResponseDto> accessDeniedExceptionHandler(AccessDeniedException ex) {
        return ResponseDto.ofError(
                ex.getExceptionCode(),
                messageSource.getMessage(ex.getExceptionCode()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({JWTVerificationException.class})
    public ResponseEntity<ResponseDto> tokenExceptionHandler(JWTVerificationException ex) {
        return ResponseDto.ofError(
                ExceptionCode.JWT_TOKEN_EXCEPTION,
                StringUtils.hasLength(ex.getMessage())
                        ? ex.getMessage()
                        : messageSource.getMessage(ExceptionCode.JWT_TOKEN_EXCEPTION),
                HttpStatus.OK);
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

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, "Can't read request", headers, status, request);
    }
}