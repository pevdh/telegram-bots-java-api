package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.Message;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class SendStickerRequest implements ApiRequest<Message> {

    private Map<String, String> args = new HashMap<>();
    private RequestStrategy requestStrategy;

    public SendStickerRequest(int chatId, File sticker) {
        this(chatId, sticker, null);
    }

    public SendStickerRequest(int chatId, File sticker, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));

        if (optionalArgs != null)
            args.putAll(optionalArgs.options());

        requestStrategy = new MultipartStrategy(sticker, "sticker");
    }

    public SendStickerRequest(int chatId, String sticker) {
        this(chatId, sticker, null);
    }

    public SendStickerRequest(int chatId, String sticker, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("sticker", sticker);

        if (optionalArgs != null)
            args.putAll(optionalArgs.options());

        requestStrategy = new PostStrategy();
    }


    @Override
    public String getMethodName() {
        return "sendSticker";
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
        return "SendStickerRequest{" +
                "args=" + args +
                ", requestStrategy=" + requestStrategy +
                '}';
    }
}
