package fis.pms.controllerAdvice;

import fis.pms.controllerAdvice.errorForm.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginException.class)
    public ErrorResult loginError(LoginException loginException){
        return new ErrorResult(400L, loginException.getMessage());
    }

}
