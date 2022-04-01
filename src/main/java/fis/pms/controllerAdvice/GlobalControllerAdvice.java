package fis.pms.controllerAdvice;

import fis.pms.controllerAdvice.errorForm.ErrorResult;
import fis.pms.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginException.class)
    public ErrorResult loginError(LoginException loginException){
        return new ErrorResult(401L, loginException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OfficeException.class)
    public ErrorResult officeError(OfficeException officeException){
        return new ErrorResult(401L, officeException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FilesException.class)
    public ErrorResult fileError(FilesException fileException){
        return new ErrorResult(401L, fileException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(VolumeException.class)
    public ErrorResult volumeError(VolumeException volumeException){
        return new ErrorResult(401L, volumeException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PlanException.class)
    public ErrorResult planError(PlanException planException){
        return new ErrorResult(401L, planException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WorkListException.class)
    public ErrorResult workListError(WorkListException workListException){
        return new ErrorResult(401L, workListException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WorkerException.class)
    public ErrorResult workerError(WorkerException workerException){
        return new ErrorResult(401L, workerException.getMessage());
    }
}
