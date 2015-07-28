package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.Message;

import java.util.HashMap;
import java.util.Map;

public class ForwardMessageRequest implements ApiRequest<Message> {

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
    public String getMethodName() {
        return "forwardMessage";
    }

    @Override
    public ResultTypes getResultType() {
        return ResultTypes.MESSAGE;
    }

    @Override
    public Map<String, String> getArgs() {
        return args;
    }

    @Override
    public RequestStrategy getRequestStrategy() {
        return new PostStrategy();
    }
}
