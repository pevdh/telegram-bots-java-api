package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class ForwardMessageRequest extends ApiRequest<Message> {

    private int chatId;
    private int fromChatId;
    private int messageId;

    public ForwardMessageRequest(int chatId, int fromChatId, int messageId) {
        this.chatId = chatId;
        this.fromChatId = fromChatId;
        this.messageId = messageId;
    }

    @Override
    protected ApiResult<Message> makeRequest(TelegramApi api) {
        Map<String, String> args = new HashMap<>();
        args.put("chat_id", String.valueOf(chatId));
        args.put("from_chat_id", String.valueOf(fromChatId));
        args.put("message_id", String.valueOf(messageId));

        String response = api.makePostRequest("forwardMessage", args);

        return deserialize(response, ResultTypes.MESSAGE);
    }
}
