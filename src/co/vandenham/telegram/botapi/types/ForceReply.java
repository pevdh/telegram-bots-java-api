package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pieter on 25-7-15.
 */
public class ForceReply {

    @SerializedName("force_reply")
    private boolean forceReply = true;

    @SerializedName("selective")
    private boolean selective = false;

    private static final ForceReply SELECTIVE = new ForceReply(true);
    private static final ForceReply NON_SELECTIVE = new ForceReply(false);

    private ForceReply(boolean selective) {
        this.selective = selective;
    }

    public static ForceReply getSelective() {
        return SELECTIVE;
    }

    public static ForceReply getNonSelective() {
        return NON_SELECTIVE;
    }
}
