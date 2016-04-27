package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a chat.
 * This might be a chat with a {@link User} or a {@link GroupChat}
 */
public class Chat {

    @JsonProperty("id")
    private int id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("title")
    private String title;

    /**
     * @return Whether this is a chat with a {@link User}
     */
    public boolean isUser() {
        return title == null;
    }

    /**
     * @return Whether this is a {@link GroupChat}
     */
    public boolean isGroupChat() {
        return !isUser();
    }

    /**
     * @return Unique identifier for this chat
     */
    public int getId() {
        return id;
    }

    /**
     * Returns this chat as a {@link User}.
     * Before invoking, check whether this chat is actually a chat with a user
     * by calling {@link Chat#isUser()}.
     *
     * @return This chat as a {@link User} object
     */
    public User asUser() {
        return new User(id, firstName, username, lastName);
    }

    /**
     * Returns this chat as a {@link GroupChat}.
     * Before invoking this method, check whether this chat is actually a group chat
     * by calling {@link Chat#isGroupChat()}.
     *
     * @return This chat as a {@link GroupChat} object
     */
    public GroupChat asGroupChat() {
        return new GroupChat(id, title);
    }

    @Override
    public String toString() {
        if (isUser())
            return "Chat {" + asUser().toString() + "}";
        else
            return "Chat {" + asGroupChat().toString() + "}";
    }
}
