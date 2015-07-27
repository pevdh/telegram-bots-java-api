package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class SendChatActionRequest extends ApiRequest<Boolean> {

    private Map<String, String> args = new HashMap<>();

    public SendChatActionRequest(int chatId, ChatAction chatAction) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("action", chatAction.getAction());
    }

    @Override
    protected String getMethodName() {
        return "sendChatAction";
    }

    @Override
    protected ResultTypes getResultType() {
        return ResultTypes.BOOLEAN;
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
        return "SendChatActionRequest{" +
                "args=" + args +
                '}';
    }
}
