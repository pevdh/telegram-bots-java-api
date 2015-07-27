package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.ReplyMarkup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class OptionalArgs {

    private boolean disableWebPagePreview = false;
    private int replyToMessageId = -1;
    private ReplyMarkup replyMarkup = null;
    private int offset = -1;
    private int limit = -1;
    private int timeout = -1;
    private int duration = -1;
    private String caption = null;

    private Map<String, String> options;

    public Map<String, String> options() {
        options = new HashMap<>();

        if (disableWebPagePreview)
            putBoolean("disable_web_page_preview", true);

        if (replyToMessageId != -1)
            putInt("reply_to_message_id", replyToMessageId);

        if (offset != -1)
            putInt("offset", offset);

        if (limit != -1)
            putInt("limit", limit);

        if (timeout != -1)
            putInt("timeout", timeout);

        if (duration != -1)
            putInt("duration", duration);

        if (replyMarkup != null)
            options.put("reply_markup", replyMarkup.serialize());

        if (caption != null)
            options.put("caption", caption);

        return options;
    }

    private void putBoolean(String key, boolean b) {
        options.put(key, String.valueOf(b));
    }

    private void putInt(String key, int i) {
        options.put(key, String.valueOf(i));
    }

    public OptionalArgs disableWebPagePreview() {
        this.disableWebPagePreview = true;
        return this;
    }

    public OptionalArgs replyToMessageId(int replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public OptionalArgs replyMarkup(ReplyMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public OptionalArgs offset(int offset) {
        this.offset = offset;
        return this;
    }

    public OptionalArgs limit(int limit) {
        this.limit = limit;
        return this;
    }

    public OptionalArgs timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public OptionalArgs duration(int duration) {
        this.duration = duration;
        return this;
    }

    public OptionalArgs caption(String caption) {
        this.caption = caption;
        return this;
    }
}
