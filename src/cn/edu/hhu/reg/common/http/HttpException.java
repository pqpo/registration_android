package cn.edu.hhu.reg.common.http;

public class HttpException extends Exception {
    private static final long serialVersionUID = -2845821270615440236L;
    
    private final String message;
    private final Exception cause;
    
    public HttpException(Exception cause) {
        this.cause = cause;
        this.message = cause.getMessage();
    }
    
    public HttpException(String message) {
        this.cause = null;
        this.message = message;
    }
    
    @Override
    public Exception getCause() {
        return cause;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}