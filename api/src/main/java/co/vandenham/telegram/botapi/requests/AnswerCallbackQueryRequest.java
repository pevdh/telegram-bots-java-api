package co.vandenham.telegram.botapi.requests;

import java.util.HashMap;
import java.util.Map;

public final class AnswerCallbackQueryRequest implements ApiRequest<Boolean> {

    private Map<String, String> args = new HashMap<>();

    public AnswerCallbackQueryRequest(String callbackId, String text, boolean showAlert) {
        args.put("callback_query_id", callbackId);
        args.put("text", text);
        args.put("show_alert", "" + showAlert);
    }

    @Override
    public String getMethodName() {
        return "answerCallbackQuery";
    }

    @Override
    public ResultTypes getResultType() {
        return ResultTypes.BOOLEAN;
    }

    @Override
    public Map<String, String> getArgs() {
        return args;
    }

    @Override
    public RequestStrategy getRequestStrategy() {
        return new PostStrategy();
    }

    @Override
    public String toString() {
        return "AnswerCallbackQueryRequest{" +
                "args=" + args +
                '}';
    }
}
