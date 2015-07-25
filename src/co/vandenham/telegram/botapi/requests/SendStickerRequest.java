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
public class SendStickerRequest extends ApiRequest<Message> {

    private int chatId;
    private File stickerFile;
    private String stickerString;
    private int replyToMessageId;
    private ReplyMarkup replyMarkup;

    private SendStickerRequest(Builder builder) {
        chatId = builder.chatId;
        stickerFile = builder.stickerFile;
        stickerString = builder.stickerString;
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
        if (stickerFile != null) {
            response = api.makeMultipartRequest("sendSticker", args, "sticker", stickerFile);
        } else {
            args.put("sticker", stickerString);
            response = api.makePostRequest("sendSticker", args);
        }
        return deserialize(response, ResultTypes.MESSAGE);
    }

    public static class Builder {

        private int chatId;
        private File stickerFile;
        private String stickerString;

        private int replyToMessageId = -1;
        private ReplyMarkup replyMarkup = null;

        public Builder(int chatId, File sticker) {
            this.chatId = chatId;
            this.stickerFile = sticker;
        }

        public Builder(int chatId, String sticker) {
            this.chatId = chatId;
            this.stickerString = sticker;
        }

        public Builder setReplyToMessageId(int replyToMessageId) {
            this.replyToMessageId = replyToMessageId;
            return this;
        }

        public Builder setReplyMarkup(ReplyMarkup replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public SendStickerRequest build() {
            return new SendStickerRequest(this);
        }
    }

}
