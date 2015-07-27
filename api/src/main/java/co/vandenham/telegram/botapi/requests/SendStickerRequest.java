package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.Message;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class SendStickerRequest extends ApiRequest<Message> {

    private Map<String, String> args = new HashMap<>();
    private RequestStrategy requestStrategy;

    public SendStickerRequest(int chatId, File sticker) {
        this(chatId, sticker, null);
    }

    public SendStickerRequest(int chatId, File sticker, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));

        if (optionalArgs != null)
            copyMap(optionalArgs.options(), args);

        requestStrategy = new MultipartStrategy(sticker, "sticker");
    }

    public SendStickerRequest(int chatId, String sticker) {
        this(chatId, sticker, null);
    }

    public SendStickerRequest(int chatId, String sticker, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("sticker", sticker);

        if (optionalArgs != null)
            copyMap(optionalArgs.options(), args);

        requestStrategy = new PostStrategy();
    }


    @Override
    protected String getMethodName() {
        return "sendSticker";
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
        return "SendStickerRequest{" +
                "args=" + args +
                ", requestStrategy=" + requestStrategy +
                '}';
    }
}
