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
public class InlineKeyboardMarkup extends ReplyMarkup {

    @JsonProperty("inline_keyboard")
    private List<List<InlineKeyboardButton>> inline_keyboard;

    private InlineKeyboardMarkup(Builder builder) {
        inline_keyboard = builder.inline_keyboard;
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

        private List<List<InlineKeyboardButton>> inline_keyboard;

        public Builder() {
            inline_keyboard = new ArrayList<>();
        }

        /**
         * Adds a row of "buttons" to the keyboard.
         *
         * @param buttons the row of buttons, represented as a String array.
         * @return This Builder instance, to allow method chaining.
         */
        public Builder row(InlineKeyboardButton... buttons) {
            inline_keyboard.add(Arrays.asList(buttons));
            return this;
        }

        /**
         * Adds all {@code buttons} with a maximum {@code width} per row.
         *
         * @param width The maximum amount of buttons per row.
         * @param buttons The buttons to add.
         * @return This Builder instance, to allow method chaining.
         */
        public Builder add(int width, InlineKeyboardButton... buttons) {
            List<InlineKeyboardButton> row = new ArrayList<>(width);
            for (int i = 0; i < buttons.length; i++) {
                row.add(buttons[i]);

                if ((i + 1) % width == 0) {
                    inline_keyboard.add(row);
                    row.clear();
                }
            }

            if (row.size() > 0)
                inline_keyboard.add(row);

            return this;
        }

        /**
         * Builds the {@link InlineKeyboardMarkup} object.
         *
         * @return The freshly created {@link InlineKeyboardMarkup}
         */
        public InlineKeyboardMarkup build() {
            return new InlineKeyboardMarkup(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InlineKeyboardMarkup{");
        sb.append("keyboard=").append(inline_keyboard);
        sb.append('}');
        return sb.toString();
    }
}
