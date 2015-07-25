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

    private int chatId;
    private float latitude;
    private float longitude;
    private int replyToMessageId;
    private ReplyMarkup replyMarkup;

    private SendLocationRequest(Builder builder) {
        chatId = builder.chatId;
        latitude = builder.latitude;
        longitude = builder.longitude;
        replyToMessageId = builder.replyToMessageId;
        replyMarkup = builder.replyMarkup;
    }

    @Override
    protected ApiResult<Message> makeRequest(TelegramApi api) {
        Map<String, String> args = new HashMap<>();
        args.put("chat_id", String.valueOf(chatId));
        args.put("latitude", String.valueOf(latitude));
        args.put("longitude", String.valueOf(longitude));

        if (replyToMessageId != -1)
            args.put("reply_to_message_id", String.valueOf(replyToMessageId));

        if (replyMarkup != null)
            args.put("reply_markup", replyMarkup.serialize());

        String response = api.makePostRequest("sendLocation", args);
        return deserialize(response, ResultTypes.MESSAGE);
    }

    public static class Builder {

        private int chatId;
        private float latitude;
        private float longitude;

        private int replyToMessageId = -1;
        private ReplyMarkup replyMarkup = null;

        public Builder(int chatId, float latitude, float longitude) {
            this.chatId = chatId;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Builder setReplyToMessageId(int replyToMessageId) {
            this.replyToMessageId = replyToMessageId;
            return this;
        }

        public Builder setReplyMarkup(ReplyMarkup replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public SendLocationRequest build() {
            return new SendLocationRequest(this);
        }
    }
}
