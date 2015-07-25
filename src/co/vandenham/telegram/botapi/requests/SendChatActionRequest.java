package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class SendChatActionRequest extends ApiRequest<Boolean> {

    private int chatId;
    private ChatAction chatAction;

    public SendChatActionRequest(int chatId, ChatAction chatAction) {
        this.chatId = chatId;
        this.chatAction = chatAction;
    }

    @Override
    protected ApiResult<Boolean> makeRequest(TelegramApi api) {
        Map<String, String> args = new HashMap<>();
        args.put("chat_id", String.valueOf(chatId));
        args.put("action", chatAction.getAction());

        String response = api.makePostRequest("sendChatAction", args);
        return deserialize(response, ResultTypes.BOOLEAN);
    }
}
