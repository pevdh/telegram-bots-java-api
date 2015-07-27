package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.Message;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class SendDocumentRequest extends ApiRequest<Message> {

    private Map<String, String> args = new HashMap<>();
    private RequestStrategy requestStrategy;

    public SendDocumentRequest(int chatId, File document) {
        this(chatId, document, null);
    }

    public SendDocumentRequest(int chatId, File document, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));

        if (optionalArgs != null)
            copyMap(optionalArgs.options(), args);

        requestStrategy = new MultipartStrategy(document, "document");
    }

    public SendDocumentRequest(int chatId, String document) {
        this(chatId, document, null);
    }

    public SendDocumentRequest(int chatId, String document, OptionalArgs optionalArgs) {
        args.put("chat_id", String.valueOf(chatId));
        args.put("document", document);

        if (optionalArgs != null)
            copyMap(optionalArgs.options(), args);

        requestStrategy = new PostStrategy();
    }

    @Override
    protected String getMethodName() {
        return "sendDocument";
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
        return "SendDocumentRequest{" +
                "args=" + args +
                ", requestStrategy=" + requestStrategy +
                '}';
    }
}
