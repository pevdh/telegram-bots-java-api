package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pieter on 25-7-15.
 */
public class GroupChat {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    public GroupChat() {
    }

    public GroupChat(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
