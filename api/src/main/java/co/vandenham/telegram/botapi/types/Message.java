package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * This object represents a message.
 *
 * Any getters labeled <i>optional</i> might return a default value (such as {@code null}).
 *
 * @see <a href="https://core.telegram.org/bots/api#message">https://core.telegram.org/bots/api#message</a>
 */
public class Message implements Updatable {

    @JsonProperty("message_id")
    private int messageId;

    @JsonProperty("from")
    private User from;

    @JsonProperty("date")
    private int date;

    @JsonProperty("chat")
    private Chat chat;

    @JsonProperty("forward_from")
    private User forwardFrom;

    @JsonProperty("forward_date")
    private int forwardDate;

    @JsonProperty("reply_to_message")
    private Message replyToMessage;

    @JsonProperty("text")
    private String text;

    @JsonProperty("audio")
    private Audio audio;

    @JsonProperty("document")
    private Document document;

    @JsonProperty("photo")
    private List<PhotoSize> photo;

    @JsonProperty("sticker")
    private Sticker sticker;

    @JsonProperty("video")
    private Video video;

    @JsonProperty("contact")
    private Contact contact;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("new_chat_participant")
    private User newChatParticipant;

    @JsonProperty("left_chat_participant")
    private User leftChatParticipant;

    @JsonProperty("new_chat_title")
    private String newChatTitle;

    @JsonProperty("new_chat_photo")
    private List<PhotoSize> newChatPhoto;

    @JsonProperty("delete_chat_photo")
    private boolean deleteChatPhoto;

    @JsonProperty("group_chat_created")
    private boolean groupChatCreated;

    @JsonProperty("caption")
    private String caption;

    private Type type;

    /**
     * @return Unique message identifier
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * @return Sender
     */
    public User getFrom() {
        return from;
    }

    /**
     * @return Date the message was sent in Unix time
     */
    public int getDate() {
        return date;
    }

    /**
     * @return Conversation the message belongs to — {@link User} in case of a private message, {@link GroupChat} in case of a group
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * <i>Optional.</i>
     *
     * @return For forwarded messages, sender of the original message
     */
    public User getForwardFrom() {
        return forwardFrom;
    }

    /**
     * <i>Optional.</i>
     *
     * @return For forwarded messages, date the original message was sent in Unix time
     */
    public int getForwardDate() {
        return forwardDate;
    }

    /**
     * <i>Optional.</i>
     *
     * @return For replies, the original message
     */
    public Message getReplyToMessage() {
        return replyToMessage;
    }

    /**
     * <i>Optional.</i>
     *
     * @return For text messages, the actual UTF-8 text of the message
     */
    public String getText() {
        return text;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Message is an audio file, information about the file
     */
    public Audio getAudio() {
        return audio;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Message is a general file, information about the file
     */
    public Document getDocument() {
        return document;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Message is a photo, available sizes of the photo
     */
    public List<PhotoSize> getPhoto() {
        return photo;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Message is a sticker, information about the sticker
     */
    public Sticker getSticker() {
        return sticker;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Message is a video, information about the video
     */
    public Video getVideo() {
        return video;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Message is a shared contact, information about the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Message is a shared location, information about the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * <i>Optional.</i>
     *
     * @return A new member was added to the group, information about them (this member may be bot itself)
     */
    public User getNewChatParticipant() {
        return newChatParticipant;
    }

    /**
     * <i>Optional.</i>
     *
     * @return A member was removed from the group, information about them (this member may be bot itself)
     */
    public User getLeftChatParticipant() {
        return leftChatParticipant;
    }

    /**
     * <i>Optional.</i>
     *
     * @return A group title was changed to this value
     */
    public String getNewChatTitle() {
        return newChatTitle;
    }

    /**
     * <i>Optional.</i>
     *
     * @return A group photo was changed to this value
     */
    public List<PhotoSize> getNewChatPhoto() {
        return newChatPhoto;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Informs that the group photo was deleted
     */
    public boolean isDeleteChatPhoto() {
        return deleteChatPhoto;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Informs that the group has been created
     */
    public boolean isGroupChatCreated() {
        return groupChatCreated;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Text description of the photo or the video
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Returns the {@link co.vandenham.telegram.botapi.types.Message.Type} of this Message.
     *
     * This can be used to verify that a Message is the expected type.
     * For example, call {@code getType() == Message.Type.Document} first before calling {@link Message#getDocument()}.
     *
     * @return The {@code Type} of this {@code Message}
     */
    public Type getType() {
        if (type == null)
            determineType();
        return type;
    }

    private void determineType() {
        if (text != null)
            type = Type.TEXT;

        else if (audio != null)
            type = Type.AUDIO;

        else if (document != null)
            type = Type.DOCUMENT;

        else if (photo != null)
            type = Type.PHOTO;

        else if (sticker != null)
            type = Type.STICKER;

        else if (video != null)
            type = Type.VIDEO;

        else if (contact != null)
            type = Type.CONTACT;

        else if (location != null)
            type = Type.LOCATION;

        else if (newChatParticipant != null)
            type = Type.NEW_CHAT_PARTICIPANT;

        else if (leftChatParticipant != null)
            type = Type.LEFT_CHAT_PARTICIPANT;

        else if (newChatPhoto != null)
            type = Type.NEW_CHAT_PHOTO;

        else if (deleteChatPhoto)
            type = Type.DELETE_CHAT_PHOTO;

        else if (groupChatCreated)
            type = Type.GROUP_CHAT_CREATED;

        else
            type = Type.UNKNOWN;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("type=").append(type);
        sb.append(", messageId=").append(messageId);
        sb.append(", from=").append(from);
        sb.append(", date=").append(date);
        sb.append(", chat=").append(chat);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Defines the different types of Messages that can be received.
     */
    public enum Type {
        TEXT,
        DOCUMENT,
        AUDIO,
        PHOTO,
        STICKER,
        VIDEO,
        CONTACT,
        LOCATION,
        NEW_CHAT_PARTICIPANT,
        LEFT_CHAT_PARTICIPANT,
        NEW_CHAT_PHOTO,
        DELETE_CHAT_PHOTO,
        GROUP_CHAT_CREATED,
        UNKNOWN
    }
}
