package fis.pms.exception;

public class FilesException extends RuntimeException {

    private Long id;

    public FilesException(Exception e) {
    }

    public FilesException(String message) {
        super(message);
    }

    public FilesException(Long id, String message) {
        super(message);
        this.id = id;
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
