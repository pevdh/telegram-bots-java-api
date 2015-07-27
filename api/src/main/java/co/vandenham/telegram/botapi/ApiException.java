package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.requests.ApiResult;

/**
 * Created by pieter on 24-7-15.
 */
public class ApiException extends RuntimeException {

    private String methodName;

    public ApiException(String methodName) {
        super(String.format("An exception occurred while accessing %s.", methodName));
        this.methodName = methodName;
    }

    public ApiException(String methodName, Throwable cause) {
        super(String.format("An exception occurred while accessing %s.", methodName), cause);
        this.methodName = methodName;
    }

    public ApiException(String methodName, ApiResult<?> unexpectedResult) {
        super(String.format("Unexpected result received from Telegram while accessing %s: %s.", methodName, unexpectedResult));
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        if (getCause() == null)
            return super.toString();
        else
            return super.toString() + " Caused by: " + getCause().toString();
    }
}
