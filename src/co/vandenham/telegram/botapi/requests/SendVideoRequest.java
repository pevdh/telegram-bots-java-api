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
public class SendVideoRequest extends ApiRequest<Message> {

    private int chatId;
    private File videoFile;
    private String videoString;

    private int duration;
    private String caption;
    private int replyToMessageId;
    private ReplyMarkup replyMarkup;

    private SendVideoRequest(Builder builder) {
        chatId = builder.chatId;
        videoFile = builder.videoFile;
        videoString = builder.videoString;
        duration = builder.duration;
        caption = builder.caption;
        replyToMessageId = builder.replyToMessageId;
        replyMarkup = builder.replyMarkup;
    }

    @Override
    protected ApiResult<Message> makeRequest(TelegramApi api) {
        Map<String, String> args = new HashMap<>();
        args.put("chat_id", String.valueOf(chatId));

        if (duration != -1)
            args.put("duration", String.valueOf(duration));

        if (caption != null)
            args.put("caption", caption);

        if (replyToMessageId != -1)
            args.put("reply_to_message_id", String.valueOf(replyToMessageId));

        if (replyMarkup != null)
            args.put("reply_markup", replyMarkup.serialize());

        String response;
        if (videoFile != null) {
            response = api.makeMultipartRequest("sendVideo", args, "video", videoFile);
        } else {
            args.put("video", videoString);
            response = api.makePostRequest("sendVideo", args);
        }
        return deserialize(response, ResultTypes.MESSAGE);
    }

    public static class Builder {

        private int chatId;
        private File videoFile;
        private String videoString;

        private int duration = -1;
        private String caption = null;
        private int replyToMessageId = -1;
        private ReplyMarkup replyMarkup = null;

        public Builder(int chatId, File video) {
            this.chatId = chatId;
            this.videoFile = video;
        }

        public Builder(int chatId, String video) {
            this.chatId = chatId;
            this.videoString = video;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
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

        public SendVideoRequest build() {
            return new SendVideoRequest(this);
        }
    }
}
