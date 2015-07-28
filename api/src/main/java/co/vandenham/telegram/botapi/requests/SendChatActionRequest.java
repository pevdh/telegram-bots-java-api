package co.vandenham.telegram.botapi.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class SendChatActionRequest implements ApiRequest<Boolean> {

    private Map<String, String> args = new HashMap<>();

    public SendChatActionRequest(int chatId, ChatAction chatAction) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("action", chatAction.getAction());
    }

    @Override
    public String getMethodName() {
        return "sendChatAction";
    }

    @Override
    public ResultTypes getResultType() {
        return ResultTypes.BOOLEAN;
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
        return "SendChatActionRequest{" +
                "args=" + args +
                '}';
    }
}
