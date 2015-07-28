package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.Message;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SendPhotoRequest implements ApiRequest<Message> {

    private Map<String, String> args = new HashMap<>();
    private RequestStrategy requestStrategy;

    public SendPhotoRequest(int chatId, File photo) {
        this(chatId, photo, null);
    }

    public SendPhotoRequest(int chatId, File photo, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));

        if (optionalArgs != null)
            args.putAll(optionalArgs.options());

        requestStrategy = new MultipartStrategy(photo, "photo");
    }

    public SendPhotoRequest(int chatId, String photo) {
        this(chatId, photo, null);
    }

    public SendPhotoRequest(int chatId, String photo, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("photo", photo);

        if (optionalArgs != null)
            args.putAll(optionalArgs.options());

        requestStrategy = new PostStrategy();
    }

    @Override
    public String getMethodName() {
        return "sendPhoto";
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
        return "SendPhotoRequest{" +
                "args=" + args +
                ", requestStrategy=" + requestStrategy +
                '}';
    }
}
