package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * This object represents an incoming callback query from a callback button in an inline keyboard.
 *
 * If the button that originated the query was attached to a message sent by the bot, the field </i>message</i> will be
 * present. If the button was attached to a message sent via the bot (in inline mode), the field <i>inline_message_id</i>
 * will be present.
 *
 * @see <a href="https://core.telegram.org/bots/api#callbackquery">the Telegram Bot API</a> for more information
 */
public class CallbackQuery implements Updatable {
    @SerializedName("id")
    private String id;

    @SerializedName("from")
    private User from;

    @SerializedName("message")
    private Message message;

    @SerializedName("inline_message_id")
    private String inlineMessageId;

    @SerializedName("data")
    private String data;

    private Type type;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CallbackQuery{");
        sb.append("type=").append(type);
        sb.append(", from=").append(from);
        sb.append(", message=").append(message);
        sb.append(", inline_message_id=").append(inlineMessageId);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }

    private void determineType() {
        if (message != null)
            type = Type.MESSAGE;

        else if (inlineMessageId != null)
            type = Type.INLINE;

        else
            type = Type.UNKNOWN;
    }

    /**
     * Defines the different types of Callbacks that can be received.
     */
    public enum Type {
        MESSAGE,
        INLINE,
        UNKNOWN
    }

    public String getData() {
        return data;
    }

    public User getFrom() {
        return from;
    }

    public String getId() {
        return id;
    }

    public String getInlineMessageId() {
        return inlineMessageId;
    }

    public Message getMessage() {
        return message;
    }

    public Type getType() {
        if (type == null) {
            determineType();
        }
        return type;
    }
}
