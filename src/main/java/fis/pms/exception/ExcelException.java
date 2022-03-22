package fis.pms.exception;

public class ExcelException extends Exception {
    public ExcelException() {
        super();
    }

    public ExcelException(String message) {
        super(message);
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
