package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * This object represents a group chat.
 *
 * @see <a href="https://core.telegram.org/bots/api#groupchat">https://core.telegram.org/bots/api#groupchat</a>
 */
public class GroupChat {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    /**
     * Parameterless constructor for gson.
     */
    public GroupChat() {
    }

    public GroupChat(int id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
     * @return Unique identifier for this group chat
     */
    public int getId() {
        return id;
    }

    /**
     * @return Group name
     */
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GroupChat{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
