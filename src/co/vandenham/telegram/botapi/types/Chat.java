package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

import java.security.acl.Group;

/**
 * Created by pieter on 25-7-15.
 */
public class Chat {

    @SerializedName("id")
    private int id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("username")
    private String username;

    @SerializedName("title")
    private String title;

    public Chat() {
    }

    public boolean isUser() {
        return title == null;
    }

    public boolean isGroupChat() {
        return !isUser();
    }

    public User asUser() {
        User user = new User(id, firstName);
        user.setUsername(username);
        user.setLastName(lastName);
        return user;
    }

    public GroupChat asGroupChat() {
        return new GroupChat(id, title);
    }
}
