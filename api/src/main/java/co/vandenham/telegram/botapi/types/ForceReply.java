package co.vandenham.telegram.botapi.types;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pieter on 25-7-15.
 */
public class ForceReply implements ReplyMarkup {

    private static final ForceReply SELECTIVE = new ForceReply(true);
    private static final ForceReply NON_SELECTIVE = new ForceReply(false);

    @SerializedName("force_reply")
    private boolean forceReply = true;
    @SerializedName("selective")
    private boolean selective = false;

    private ForceReply(boolean selective) {
        this.selective = selective;
    }

    public static ForceReply getSelective() {
        return SELECTIVE;
    }

    public static ForceReply getNonSelective() {
        return NON_SELECTIVE;
    }

    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
