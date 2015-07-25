package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.ReplyMarkup;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class SendDocumentRequest extends ApiRequest<Message> {

    private int chatId;
    private File documentFile;
    private String documentString;
    private int replyToMessageId;
    private ReplyMarkup replyMarkup;

    private SendDocumentRequest(Builder builder) {
        chatId = builder.chatId;
        documentFile = builder.documentFile;
        documentString = builder.documentString;
        replyToMessageId = builder.replyToMessageId;
        replyMarkup = builder.replyMarkup;
    }

    @Override
    protected ApiResult<Message> makeRequest(TelegramApi api) {
        Map<String, String> args = new HashMap<>();
        args.put("chat_id", String.valueOf(chatId));

        if (replyToMessageId != -1)
            args.put("reply_to_message_id", String.valueOf(replyToMessageId));

        if (replyMarkup != null)
            args.put("reply_markup", replyMarkup.serialize());

        String response;
        if (documentFile != null) {
            response = api.makeMultipartRequest("sendDocument", args, "document", documentFile);
        } else {
            args.put("document", documentString);
            response = api.makePostRequest("sendDocument", args);
        }
        return deserialize(response, ResultTypes.MESSAGE);
    }

    public static class Builder {

        private int chatId;
        private File documentFile;
        private String documentString;

        private int replyToMessageId = -1;
        private ReplyMarkup replyMarkup = null;

        public Builder(int chatId, File document) {
            this.chatId = chatId;
            this.documentFile = document;
        }

        public Builder(int chatId, String document) {
            this.chatId = chatId;
            this.documentString = document;
        }

        public Builder setReplyToMessageId(int replyToMessageId) {
            this.replyToMessageId = replyToMessageId;
            return this;
        }

        public Builder setReplyMarkup(ReplyMarkup replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public SendDocumentRequest build() {
            return new SendDocumentRequest(this);
        }
    }
}
