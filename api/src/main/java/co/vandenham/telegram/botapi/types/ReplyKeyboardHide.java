package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Upon receiving a message with this object, Telegram clients will hide the current custom keyboard and display the default letter-keyboard.
 * By default, custom keyboards are displayed until a new keyboard is sent by a bot.
 * An exception is made for one-time keyboards that are hidden immediately after the user presses a button (see {@link ReplyKeyboardMarkup})
 *
 * @see <a href="https://core.telegram.org/bots/api#replykeyboardhide">https://core.telegram.org/bots/api#replykeyboardhide</a>
 */
public class ReplyKeyboardHide extends ReplyMarkup {

    private final static ReplyKeyboardHide NON_SELECTIVE = new ReplyKeyboardHide(false);
    private final static ReplyKeyboardHide SELECTIVE = new ReplyKeyboardHide(true);

    @JsonProperty("hide_keyboard")
    private boolean hideKeyboard = true;
    @JsonProperty("selective")
    private boolean selective = false;

    private ReplyKeyboardHide(boolean selective) {
        this.selective = selective;
    }

    /**
     * Returns a selective instance of this class ({@code selective} is set to {@code true}).
     *
     * @return a selective instance of this class
     */
    public static ReplyKeyboardHide getSelectiveInstance() {
        return SELECTIVE;
    }

    /**
     * Returns a non-selective instance of this class ({@code selective} is set to {@code false}).
     *
     * @return a non-selective instance of this class
     */
    public static ReplyKeyboardHide getNonSelectiveInstance() {
        return NON_SELECTIVE;
    }

}
