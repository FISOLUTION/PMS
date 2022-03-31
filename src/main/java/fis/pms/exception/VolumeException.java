package fis.pms.exception;

public class VolumeException extends RuntimeException{
    public VolumeException() {
        super();
    }

    public VolumeException(String message) {
        super(message);
    }

    public VolumeException(String message, Throwable cause) {
        super(message, cause);
    }

    public VolumeException(Throwable cause) {
        super(cause);
    }

    protected VolumeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
