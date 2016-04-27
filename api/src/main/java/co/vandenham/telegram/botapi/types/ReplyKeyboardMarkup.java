package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This object represents a custom keyboard with reply options.
 *
 * @see <a href="https://core.telegram.org/bots/api#replykeyboardmarkup">https://core.telegram.org/bots/api#replykeyboardmarkup</a>
 */
public class ReplyKeyboardMarkup extends ReplyMarkup {

    @JsonProperty("keyboard")
    private List<List<String>> keyboard;

    @JsonProperty("resize_keyboard")
    private boolean resizeKeyboard;

    @JsonProperty("one_time_keyboard")
    private boolean oneTimeKeyboard;

    @JsonProperty("selective")
    private boolean selective;

    private ReplyKeyboardMarkup(Builder builder) {
        keyboard = builder.keyboard;
        resizeKeyboard = builder.resizeKeyboard;
        oneTimeKeyboard = builder.oneTimeKeyboard;
        selective = builder.selective;
    }

    /**
     * A convenience Builder for this class.
     *
     * An example:
     * <pre>
     * {@code
     * ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup.Builder()
     *                                          .row("A", "B", "C")
     *                                          .row("D")
     *                                          .row("E", "F")
     *                                          .build();
     * }
     * </pre>
     */
    public static class Builder {

        private List<List<String>> keyboard;
        private boolean resizeKeyboard;
        private boolean oneTimeKeyboard;
        private boolean selective;

        public Builder() {
            keyboard = new ArrayList<>();
        }

        /**
         * Adds a row of "buttons" to the keyboard.
         *
         * @param buttons the row of buttons, represented as a String array.
         * @return This Builder instance, to allow method chaining.
         */
        public Builder row(String... buttons) {
            keyboard.add(Arrays.asList(buttons));
            return this;
        }

        /**
         * Adds all {@code buttons} with a maximum {@code width} per row.
         *
         * @param width The maximum amount of buttons per row.
         * @param buttons The buttons to add.
         * @return This Builder instance, to allow method chaining.
         */
        public Builder add(int width, String... buttons) {
            List<String> row = new ArrayList<>(width);
            for (int i = 0; i < buttons.length; i++) {
                row.add(buttons[i]);

                if ((i + 1) % width == 0) {
                    keyboard.add(row);
                    row.clear();
                }
            }

            if (row.size() > 0)
                keyboard.add(row);

            return this;
        }

        /**
         * Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons).
         * Defaults to false, in which case the custom keyboard is always of the same height as the app's standard keyboard.
         *
         * @return This Builder instance, to allow method chaining.
         */
        public Builder setResizeKeyboard() {
            this.resizeKeyboard = true;
            return this;
        }

        /**
         * Requests clients to hide the keyboard as soon as it's been used.
         * Defaults to false.
         *
         * @return This Builder instance, to allow method chaining.
         */
        public Builder setOneTimeKeyboard() {
            this.oneTimeKeyboard = true;
            return this;
        }

        /**
         * Use this parameter if you want to show the keyboard to specific users only.
         * Targets:
         *      1) users that are @mentioned in the text of the Message object;
         *      2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
         *
         * Example: A user requests to change the bot‘s language,
         * bot replies to the request with a keyboard to select the new language.
         * Other users in the group don’t see the keyboard.
         *
         * @return This Builder instance, to allow method chaining.
         */
        public Builder setSelective() {
            this.selective = true;
            return this;
        }

        /**
         * Builds the {@link ReplyKeyboardMarkup} object.
         *
         * @return The freshly created {@link ReplyKeyboardMarkup}
         */
        public ReplyKeyboardMarkup build() {
            return new ReplyKeyboardMarkup(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReplyKeyboardMarkup{");
        sb.append("keyboard=").append(keyboard);
        sb.append(", resizeKeyboard=").append(resizeKeyboard);
        sb.append(", oneTimeKeyboard=").append(oneTimeKeyboard);
        sb.append(", selective=").append(selective);
        sb.append('}');
        return sb.toString();
    }
}
