package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.User;

import java.util.Map;

/**
 * Created by pieter on 24-7-15.
 */
public class GetMeRequest extends ApiRequest<User> {

    @Override
    protected String getMethodName() {
        return "getMe";
    }

    @Override
    protected ResultTypes getResultType() {
        return ResultTypes.USER;
    }

    @Override
    protected Map<String, String> getArgs() {
        return null;
    }

    @Override
    protected RequestStrategy getRequestStrategy() {
        return new GetStrategy();
    }

    @Override
    public String toString() {
        return "GetMeRequest{}";
    }
}
