package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.Message;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class SendVideoRequest implements ApiRequest<Message> {

    private Map<String, String> args = new HashMap<>();
    private RequestStrategy requestStrategy;

    public SendVideoRequest(int chatId, File video) {
        this(chatId, video, null);
    }

    public SendVideoRequest(int chatId, File video, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));

        if (optionalArgs != null)
            args.putAll(optionalArgs.options());

        requestStrategy = new MultipartStrategy(video, "video");
    }

    public SendVideoRequest(int chatId, String video) {
        this(chatId, video, null);
    }

    public SendVideoRequest(int chatId, String video, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("video", video);

        if (optionalArgs != null)
            args.putAll(optionalArgs.options());

        requestStrategy = new PostStrategy();
    }

    @Override
    public String getMethodName() {
        return "sendVideo";
    }

    @Override
    public ResultTypes getResultType() {
        return ResultTypes.MESSAGE;
    }

    @Override
    public Map<String, String> getArgs() {
        return args;
    }

    @Override
    public RequestStrategy getRequestStrategy() {
        return requestStrategy;
    }

    @Override
    public String toString() {
        return "SendVideoRequest{" +
                "args=" + args +
                ", requestStrategy=" + requestStrategy +
                '}';
    }
}
