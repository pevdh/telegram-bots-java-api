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
public class SendAudioRequest extends ApiRequest<Message> {

    private int chatId;
    private File audioFile;
    private String audioString;

    private int duration;
    private int replyToMessageId;
    private ReplyMarkup replyMarkup;

    private SendAudioRequest(Builder builder) {
        chatId = builder.chatId;
        audioFile = builder.audioFile;
        audioString = builder.audioString;
        duration = builder.duration;
        replyToMessageId = builder.replyToMessageId;
        replyMarkup = builder.replyMarkup;
    }

    @Override
    protected ApiResult<Message> makeRequest(TelegramApi api) {
        Map<String, String> args = new HashMap<>();
        args.put("chat_id", String.valueOf(chatId));

        if (duration != -1)
            args.put("duration", String.valueOf(duration));

        if (replyToMessageId != -1)
            args.put("reply_to_message_id", String.valueOf(replyToMessageId));

        if (replyMarkup != null)
            args.put("reply_markup", replyMarkup.serialize());

        String response;
        if (audioFile != null) {
            response = api.makeMultipartRequest("sendAudio", args, "audio", audioFile);
        } else {
            args.put("audio", audioString);
            response = api.makePostRequest("sendAudio", args);
        }
        return deserialize(response, ResultTypes.MESSAGE);
    }

    public static class Builder {

        private int chatId;
        private File audioFile;
        private String audioString;

        private int duration = -1;
        private int replyToMessageId = -1;
        private ReplyMarkup replyMarkup = null;

        public Builder(int chatId, File audio) {
            this.chatId = chatId;
            this.audioFile = audio;
        }

        public Builder(int chatId, String audio) {
            this.chatId = chatId;
            this.audioString = audio;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
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

        public SendAudioRequest build() {
            return new SendAudioRequest(this);
        }
    }

}
