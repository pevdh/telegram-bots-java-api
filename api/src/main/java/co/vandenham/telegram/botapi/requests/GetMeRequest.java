package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.User;

import java.util.Map;

public class GetMeRequest implements ApiRequest<User> {

    @Override
    public String getMethodName() {
        return "getMe";
    }

    @Override
    public ResultTypes getResultType() {
        return ResultTypes.USER;
    }

    @Override
    public Map<String, String> getArgs() {
        return null;
    }

    @Override
    public RequestStrategy getRequestStrategy() {
        return new GetStrategy();
    }

    @Override
    public String toString() {
        return "GetMeRequest{}";
    }
}
