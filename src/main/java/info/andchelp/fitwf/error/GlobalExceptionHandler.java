package info.andchelp.fitwf.error;

import info.andchelp.fitwf.dto.response.ExceptionDto;
import info.andchelp.fitwf.error.exception.AbstractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Controller

public class GlobalExceptionHandler extends AbstractHandler {

    @RequestMapping("${server.error.path:${error.path:/error}}")
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> defaultExceptionHandler(Exception thr, WebRequest request) {
        Exception t = (Exception) request.getAttribute("javax.servlet.error.exception", 0);
        return new ResponseEntity<>("Все плохо", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AbstractException.class})
    public ResponseEntity<Object> tes(AbstractException ex, WebRequest request) {
        return new ResponseEntity<>(ExceptionDto.of(ex.getType()), HttpStatus.OK);
    }

}
//Exception t = (Exception)request.getAttribute("javax.servlet.error.exception",0);