package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This object represents one button of an inline keyboard. You must use exactly one of the optional fields by using one
 * of the available subclasses.
 *
 * @see <a href="https://core.telegram.org/bots/api#inlinekeyboardbutton">the Telegram Bot API</a>
 */
public abstract class InlineKeyboardButton extends ReplyMarkup {

    @JsonProperty("text")
    private String text;
    @JsonProperty("url")
    protected String url;
    @JsonProperty("callback_data")
    protected String data;
    @JsonProperty("switch_inline_query")
    protected String query;

    protected InlineKeyboardButton(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InlineKeyboardMarkup{");
        sb.append("text=").append(text);
        sb.append("url=").append(url);
        sb.append("callback_data=").append(data);
        sb.append("switch_inline_query=").append(query);
        sb.append('}');
        return sb.toString();
    }
}
