package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a chat.
 * This might be a chat with a {@link User} or a {@link GroupChat}
 */
public class Chat extends TelegramType {

    @JsonProperty("id")
    private int id;

    @JsonProperty("type")
    private String type;

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
     * @return Type of chat, can be either “private”, “group”, “supergroup” or “channel”
     */
    public String getType() {
        return type;
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
        final StringBuilder sb = new StringBuilder("Chat{");
        sb.append("id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", title=").append(title);
        sb.append(", username=").append(username);
        sb.append(", first_name=").append(firstName);
        sb.append(", last_name=").append(lastName);
        sb.append('}');
        return sb.toString();
    }
}
