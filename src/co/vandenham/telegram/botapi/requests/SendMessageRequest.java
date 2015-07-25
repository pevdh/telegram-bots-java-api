package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.ReplyMarkup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public final class SendMessageRequest extends ApiRequest<Message> {

    private int chatId;
    private String text;

    private boolean disableWebPagePreview;
    private int replyToMessageId;
    private ReplyMarkup replyMarkup;

    private SendMessageRequest(Builder builder) {
        chatId = builder.chatId;
        text = builder.text;

        disableWebPagePreview = builder.disableWebPagePreview;
        replyToMessageId = builder.replyToMessageId;
        replyMarkup = builder.replyMarkup;
    }

    @Override
    protected ApiResult<Message> makeRequest(TelegramApi api) {
        Map<String, String> args = new HashMap<>();
        args.put("chat_id", String.valueOf(chatId));
        args.put("text", text);

        if (disableWebPagePreview)
            args.put("disable_web_page_preview", String.valueOf(disableWebPagePreview));

        if (replyToMessageId != -1)
            args.put("reply_to_message_id", String.valueOf(replyToMessageId));

        if (replyMarkup != null)
            args.put("reply_markup", replyMarkup.serialize());

        String response = api.makePostRequest("sendMessage", args);
        return deserialize(response, ResultTypes.MESSAGE);
    }

    public static class Builder {

        private int chatId;
        private String text;

        private boolean disableWebPagePreview = false;
        private int replyToMessageId = -1;
        private ReplyMarkup replyMarkup = null;

        public Builder(int chatId, String text) {
            this.chatId = chatId;
            this.text = text;
        }

        public Builder disableWebPagePreview() {
            this.disableWebPagePreview = true;
            return this;
        }

        public Builder setReplyToMessageId(int id) {
            this.replyToMessageId = id;
            return this;
        }

        public Builder setReplyMarkup(ReplyMarkup markup) {
            this.replyMarkup = markup;
            return this;
        }

        public SendMessageRequest build() {
            return new SendMessageRequest(this);
        }

    }
}
