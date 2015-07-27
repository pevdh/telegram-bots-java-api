package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class GetUpdatesRequest implements ApiRequest<List<Update>> {

    private Map<String, String> args = new HashMap<>();

    public GetUpdatesRequest() {
    }

    public GetUpdatesRequest(OptionalArgs optionalArgs) {
        if (optionalArgs != null)
            args.putAll(optionalArgs.options());
    }

    @Override
    public String getMethodName() {
        return "getUpdates";
    }

    @Override
    public ResultTypes getResultType() {
        return ResultTypes.LIST_OF_UPDATES;
    }

    @Override
    public Map<String, String> getArgs() {
        return args;
    }

    @Override
    public RequestStrategy getRequestStrategy() {
        return new PostStrategy();
    }

    @Override
    public String toString() {
        return "GetUpdatesRequest{" +
                "args=" + args +
                '}';
    }
}
