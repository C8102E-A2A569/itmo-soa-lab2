package c8102ea2a569.service2tomcat.exception;

//404
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
