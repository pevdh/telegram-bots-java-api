package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pieter on 25-7-15.
 */
public class Update {

    @SerializedName("update_id")
    private int updateId;

    @SerializedName("message")
    private Message message;

    public Update() {
    }

    public int getUpdateId() {
        return updateId;
    }

    public Message getMessage() {
        return message;
    }
}
