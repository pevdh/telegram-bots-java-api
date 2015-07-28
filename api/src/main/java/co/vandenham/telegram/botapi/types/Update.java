package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * This object represents an incoming update.
 *
 * Any getters labeled <i>optional</i> might return a default value (such as {@code null}).
 *
 * @see <a href="https://core.telegram.org/bots/api#update">https://core.telegram.org/bots/api#update</a>
 */
public class Update {

    @SerializedName("update_id")
    private int updateId;

    @SerializedName("message")
    private Message message;

    /**
     * @return The update‘s unique identifier.
     * Update identifiers start from a certain positive number and increase sequentially.
     * This ID becomes especially handy if you’re using Webhooks, since it allows you to ignore repeated updates or to restore the correct update sequence, should they get out of order.
     */
    public int getUpdateId() {
        return updateId;
    }

    /**
     * <i>Optional.</i>
     *
     * @return New incoming message of any kind — text, photo, sticker, etc.
     */
    public Message getMessage() {
        return message;
    }
}
