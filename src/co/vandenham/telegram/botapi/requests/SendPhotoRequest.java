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
public class SendPhotoRequest extends ApiRequest<Message> {

    private int chatId;
    private File photoFile;
    private String photoString;

    private String caption;
    private int replyToMessageId;
    private ReplyMarkup replyMarkup;

    private SendPhotoRequest(Builder builder) {
        this.chatId = builder.chatId;
        this.photoFile = builder.photoFile;
        this.photoString = builder.photoString;
        this.caption = builder.caption;
        this.replyToMessageId = builder.replyToMessageId;
        this.replyMarkup = builder.replyMarkup;
    }

    @Override
    protected ApiResult<Message> makeRequest(TelegramApi api) {
        Map<String, String> args = new HashMap<>();
        args.put("chat_id", String.valueOf(chatId));

        if (caption != null)
            args.put("caption", caption);

        if (replyToMessageId != -1)
            args.put("reply_to_message_id", String.valueOf(replyToMessageId));

        if (replyMarkup != null)
            args.put("reply_markup", replyMarkup.serialize());

        String response;
        if (photoFile != null) {
            response = api.makeMultipartRequest("sendPhoto", args, "photo", photoFile);
        } else {
            args.put("photo", photoString);
            response = api.makePostRequest("sendPhoto", args);
        }
        return deserialize(response, ResultTypes.MESSAGE);
    }

    public static class Builder {

        private int chatId;
        private File photoFile;
        private String photoString;

        private String caption = null;
        private int replyToMessageId = -1;
        private ReplyMarkup replyMarkup = null;

        public Builder(int chatId, File photo) {
            this.chatId = chatId;
            this.photoFile = photo;
        }

        public Builder(int chatId, String photo) {
            this.photoString = photo;
            this.chatId = chatId;
        }

        public Builder setCaption(String caption) {
            this.caption = caption;
            return this;
        }

        public Builder setReplyToMessageId(int replyToMessageId) {
            this.replyToMessageId = replyToMessageId;
            return this;
        }

        public Builder setReplyMarkup(ReplyMarkup replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public SendPhotoRequest build() {
            return new SendPhotoRequest(this);
        }

    }
}
