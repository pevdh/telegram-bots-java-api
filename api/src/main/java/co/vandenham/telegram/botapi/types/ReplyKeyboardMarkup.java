package co.vandenham.telegram.botapi.types;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pieter on 25-7-15.
 */
public class ReplyKeyboardMarkup implements ReplyMarkup {

    @SerializedName("keyboard")
    private List<List<String>> keyboard;

    @SerializedName("resize_keyboard")
    private boolean resizeKeyboard;

    @SerializedName("one_time_keyboard")
    private boolean oneTimeKeyboard;

    @SerializedName("selective")
    private boolean selective;

    private ReplyKeyboardMarkup(Builder builder) {
        keyboard = builder.keyboard;
        resizeKeyboard = builder.resizeKeyboard;
        oneTimeKeyboard = builder.oneTimeKeyboard;
        selective = builder.selective;
    }

    @Override
    public String serialize() {
        return new Gson().toJson(this);
    }

    public static class Builder {

        private List<List<String>> keyboard;
        private boolean resizeKeyboard;
        private boolean oneTimeKeyboard;
        private boolean selective;

        public Builder() {
            keyboard = new ArrayList<>();
        }

        public Builder row(String... buttons) {
            keyboard.add(Arrays.asList(buttons));
            return this;
        }

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

        public Builder setResizeKeyboard(boolean resizeKeyboard) {
            this.resizeKeyboard = resizeKeyboard;
            return this;
        }

        public Builder setOneTimeKeyboard(boolean oneTimeKeyboard) {
            this.oneTimeKeyboard = oneTimeKeyboard;
            return this;
        }

        public Builder setSelective(boolean selective) {
            this.selective = selective;
            return this;
        }

        public ReplyKeyboardMarkup build() {
            return new ReplyKeyboardMarkup(this);
        }
    }

}
