package fis.pms.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class OfficeException extends RuntimeException {

    String code;

    public OfficeException(){
        super();
    }

    public OfficeException(String message) {
        super(message);
    }

    public OfficeException(String code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }
}
