package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.Message;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class SendVideoRequest extends ApiRequest<Message> {

    private Map<String, String> args = new HashMap<>();
    private RequestStrategy requestStrategy;

    public SendVideoRequest(int chatId, File video) {
        this(chatId, video, null);
    }

    public SendVideoRequest(int chatId, File video, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));

        if (optionalArgs != null)
            copyMap(optionalArgs.options(), args);

        requestStrategy = new MultipartStrategy(video, "video");
    }

    public SendVideoRequest(int chatId, String video) {
        this(chatId, video, null);
    }

    public SendVideoRequest(int chatId, String video, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("video", video);

        if (optionalArgs != null)
            copyMap(optionalArgs.options(), args);

        requestStrategy = new PostStrategy();
    }

    @Override
    protected String getMethodName() {
        return "sendVideo";
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
        return "SendVideoRequest{" +
                "args=" + args +
                ", requestStrategy=" + requestStrategy +
                '}';
    }
}
