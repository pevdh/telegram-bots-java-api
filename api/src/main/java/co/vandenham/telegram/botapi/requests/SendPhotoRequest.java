package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.Message;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class SendPhotoRequest extends ApiRequest<Message> {

    private Map<String, String> args = new HashMap<>();
    private RequestStrategy requestStrategy;

    public SendPhotoRequest(int chatId, File photo) {
        this(chatId, photo, null);
    }

    public SendPhotoRequest(int chatId, File photo, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));

        if (optionalArgs != null)
            copyMap(optionalArgs.options(), args);

        requestStrategy = new MultipartStrategy(photo, "photo");
    }

    public SendPhotoRequest(int chatId, String photo) {
        this(chatId, photo, null);
    }

    public SendPhotoRequest(int chatId, String photo, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("photo", photo);

        if (optionalArgs != null)
            copyMap(optionalArgs.options(), args);

        requestStrategy = new PostStrategy();
    }

    @Override
    protected String getMethodName() {
        return "sendPhoto";
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
        return "SendPhotoRequest{" +
                "args=" + args +
                ", requestStrategy=" + requestStrategy +
                '}';
    }
}
