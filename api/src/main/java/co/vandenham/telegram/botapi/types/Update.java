package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object represents an incoming update.
 *
 * Any getters labeled <i>optional</i> might return a default value (such as {@code null}).
 *
 * @see <a href="https://core.telegram.org/bots/api#update">https://core.telegram.org/bots/api#update</a>
 */
public class Update extends TelegramType {

    @JsonProperty("update_id")
    private int updateId;

    @JsonProperty("message")
    private Message message;

    @JsonProperty("edited_message")
    private Message editedMessage;

    @JsonProperty("callback_query")
    private CallbackQuery callbackQuery;

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

    /**
     * <i>Optional.</i>
     *
     * @return New version of a message that is known to the bot and was edited
     */
    public Message getEditedMessage() {
        return editedMessage;
    }

    /**
     * <i>Optional.</i>
     *
     * @return New incoming callback query of any kind — message or inline
     */
    public CallbackQuery getCallbackQuery() {
        return callbackQuery;
    }

}
