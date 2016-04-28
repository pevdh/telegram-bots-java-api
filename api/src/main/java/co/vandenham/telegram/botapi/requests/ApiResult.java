package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.TelegramType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResult<T> extends TelegramType {

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
