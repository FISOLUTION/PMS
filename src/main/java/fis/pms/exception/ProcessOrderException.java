package fis.pms.exception;

public class ProcessOrderException extends RuntimeException{
    public ProcessOrderException() {
        super();
    }

    public ProcessOrderException(String message) {
        super(message);
    }

    public ProcessOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessOrderException(Throwable cause) {
        super(cause);
    }

    protected ProcessOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
