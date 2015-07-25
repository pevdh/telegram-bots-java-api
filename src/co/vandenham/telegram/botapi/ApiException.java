package co.vandenham.telegram.botapi;

/**
 * Created by pieter on 24-7-15.
 */
public class ApiException extends RuntimeException {

    private String methodName;


    public ApiException(String methodName) {
        this(methodName, null);
    }

    public ApiException(String methodName, Throwable cause) {
        super("An exception occurred while accessing " + methodName, cause);
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}
