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

    private Map<String, String> args = new HashMap<>();
    private RequestStrategy requestStrategy;

    public SendAudioRequest(int chatId, File audioFile) {
        this(chatId, audioFile, null);
    }

    public SendAudioRequest(int chatId, File audioFile, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));

        if (optionalArgs != null)
            copyMap(optionalArgs.getOptions(), args);

        requestStrategy = new MultipartStrategy(audioFile, "audio");
    }

    public SendAudioRequest(int chatId, String audioString) {
        this(chatId, audioString, null);
    }

    public SendAudioRequest(int chatId, String audioString, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("audio", audioString);

        if (optionalArgs != null)
            copyMap(optionalArgs.getOptions(), args);

        requestStrategy = new PostStrategy();
    }

    @Override
    protected String getMethodName() {
        return "sendAudio";
    }

    @Override
    protected ResultTypes getResultType() {
        return ResultTypes.MESSAGE;
    }

    @Override
    protected Map<String, String> getArgs() {
        return args;
    }

    @Override
    protected RequestStrategy getRequestStrategy() {
        return requestStrategy;
    }

    @Override
    public String toString() {
        return "SendAudioRequest{" +
                "args=" + args +
                ", requestStrategy=" + requestStrategy +
                '}';
    }
}
