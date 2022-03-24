package fis.pms.exception;

public class FilesException extends Exception {

    private Long id;

    public FilesException() {
    }

    public FilesException(String message) {
        super(message);
    }

    public FilesException(Long id, String message) {
        this.id = id;
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
