package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.requests.ApiResult;

/**
 * Created by pieter on 24-7-15.
 */
public class ApiException extends RuntimeException {

    private String methodName;


    public ApiException(String methodName) {
        super("An exception occurred while accessing " + methodName);
    }

    public ApiException(String methodName, Throwable cause) {
        super("An exception occurred while accessing " + methodName, cause);
        this.methodName = methodName;
    }

    public ApiException(String methodName, ApiResult<?> unexpectedResult) {
        super(String.format("Unexpected result received from Telegram while accessing %s: %s", methodName, unexpectedResult));
    }

    public String getMethodName() {
        return methodName;
    }
}
