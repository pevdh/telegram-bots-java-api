package co.vandenham.telegram.botapi.types;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pieter on 25-7-15.
 */
public class ReplyKeyboardHide implements ReplyMarkup {

    @SerializedName("hide_keyboard")
    private boolean hideKeyboard = true;

    @SerializedName("selective")
    private boolean selective = false;

    private final static ReplyKeyboardHide NON_SELECTIVE = new ReplyKeyboardHide(false);
    private final static ReplyKeyboardHide SELECTIVE = new ReplyKeyboardHide(true);

    public ReplyKeyboardHide(boolean selective) {
        this.selective = selective;
    }

    public static ReplyKeyboardHide getSelectiveInstance() {
        return SELECTIVE;
    }

    public static ReplyKeyboardHide getNonSelectiveInstance() {
        return NON_SELECTIVE;
    }

    @Override
    public String serialize() {
        return new Gson().toJson(this);
    }
}
