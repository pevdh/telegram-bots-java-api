package co.vandenham.telegram.botapi.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResult<T> {

    @JsonProperty("ok")
    private boolean ok;

    @JsonProperty("description")
    private String description;

    @JsonProperty("error_code")
    private int errorCode;

    @JsonProperty("result")
    private T result;

    public ApiResult() {
    }

    public boolean isOk() {
        return ok;
    }

    public String getDescription() {
        return description;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public T getResult() {
        return result;
    }
}
