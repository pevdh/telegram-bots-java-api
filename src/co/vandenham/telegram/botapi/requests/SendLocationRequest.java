package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.ReplyMarkup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class SendLocationRequest extends ApiRequest<Message> {

    private Map<String, String> args = new HashMap<>();

    public SendLocationRequest(int chatId, float latitude, float longitude) {
        this(chatId, latitude, longitude, null);
    }

    public SendLocationRequest(int chatId, float latitude, float longitude, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("latitude", String.valueOf(latitude));
        args.put("longitude", String.valueOf(longitude));

        if (optionalArgs != null)
            copyMap(optionalArgs.getOptions(), args);
    }

    @Override
    protected String getMethodName() {
        return "sendLocation";
    }

    @Override
    protected ResultTypes getResultType() {
        return ResultTypes.MESSAGE;
    }

    @Override
    protected Map<String, String> getArgs() {
        return args;
    }

    @Override
    protected RequestStrategy getRequestStrategy() {
        return new PostStrategy();
    }

    @Override
    public String toString() {
        return "SendLocationRequest{" +
                "args=" + args +
                '}';
    }
}
