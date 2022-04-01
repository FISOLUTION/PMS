package fis.pms.exception;

public class WorkerException extends RuntimeException {

    public WorkerException(String message) {
        super(message);
    }

    public WorkerException() {
        super();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
