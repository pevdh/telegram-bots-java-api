package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public final class SendMessageRequest extends ApiRequest<Message> {

    private Map<String, String> args = new HashMap<>();

    public SendMessageRequest(int chatId, String text) {
        this(chatId, text, null);
    }

    public SendMessageRequest(int chatId, String text, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("text", text);

        if (optionalArgs != null)
            copyMap(optionalArgs.options(), args);
    }

    @Override
    protected String getMethodName() {
        return "sendMessage";
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
        return "SendMessageRequest{" +
                "args=" + args +
                '}';
    }
}
