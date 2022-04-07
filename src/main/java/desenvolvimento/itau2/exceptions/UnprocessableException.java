package desenvolvimento.itau2.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableException extends RuntimeException{

    public UnprocessableException(String message) {
        super(message);
    }

}
