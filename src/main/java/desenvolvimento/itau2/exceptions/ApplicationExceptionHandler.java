package desenvolvimento.itau2.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleException(Exception e){
        log.info("Caiu na excessão 404");
        return new ResponseEntity("Dados não foram encontrados", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UnprocessableException.class)
    public ResponseEntity unprocessableException(Exception e){
        log.info("Caiu na excessão 422");
        return new ResponseEntity("Erro - Não foi possivel validar os dados" , HttpStatus.UNPROCESSABLE_ENTITY);
    }


}

