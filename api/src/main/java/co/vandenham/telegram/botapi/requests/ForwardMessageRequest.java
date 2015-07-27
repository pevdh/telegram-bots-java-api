package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class ForwardMessageRequest extends ApiRequest<Message> {

    private Map<String, String> args = new HashMap<>();

    public ForwardMessageRequest(int chatId, int fromChatId, int messageId) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("from_chat_id", String.valueOf(fromChatId));
        args.put("message_id", String.valueOf(messageId));
    }

    @Override
    public String toString() {
        return "ForwardMessageRequest{" +
                "args=" + args +
                '}';
    }

    @Override
    protected String getMethodName() {
        return "forwardMessage";
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
}
