package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pieter on 25-7-15.
 */
public class Message {

    @SerializedName("message_id")
    private int messageId;

    @SerializedName("from")
    private User from;

    @SerializedName("date")
    private int date;

    @SerializedName("chat")
    private Chat chat;

    @SerializedName("forward_from")
    private User forwardFrom;

    @SerializedName("forward_date")
    private int forwardDate;

    @SerializedName("reply_to_message")
    private Message replyToMessage;

    @SerializedName("text")
    private String text;

    @SerializedName("audio")
    private Audio audio;

    @SerializedName("document")
    private Document document;

    @SerializedName("photo")
    private List<PhotoSize> photo;

    @SerializedName("sticker")
    private Sticker sticker;

    @SerializedName("video")
    private Video video;

    @SerializedName("contact")
    private Contact contact;

    @SerializedName("location")
    private Location location;

    @SerializedName("new_chat_participant")
    private User newChatParticipant;

    @SerializedName("left_chat_participant")
    private User leftChatParticipant;

    @SerializedName("new_chat_title")
    private String newChatTitle;

    @SerializedName("new_chat_photo")
    private List<PhotoSize> newChatPhoto;

    @SerializedName("delete_chat_photo")
    private boolean deleteChatPhoto;

    @SerializedName("group_chat_created")
    private boolean groupChatCreated;

    @SerializedName("caption")
    private String caption;

    private Type type;

    public enum Type {
        TEXT, DOCUMENT, AUDIO, PHOTO, STICKER, VIDEO, CONTACT, LOCATION, UNKNOWN
    }

    public int getMessageId() {
        return messageId;
    }

    public User getFrom() {
        return from;
    }

    public int getDate() {
        return date;
    }

    public Chat getChat() {
        return chat;
    }

    public User getForwardFrom() {
        return forwardFrom;
    }

    public int getForwardDate() {
        return forwardDate;
    }

    public Message getReplyToMessage() {
        return replyToMessage;
    }

    public String getText() {
        return text;
    }

    public Audio getAudio() {
        return audio;
    }

    public Document getDocument() {
        return document;
    }

    public List<PhotoSize> getPhoto() {
        return photo;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public Video getVideo() {
        return video;
    }

    public Contact getContact() {
        return contact;
    }

    public Location getLocation() {
        return location;
    }

    public User getNewChatParticipant() {
        return newChatParticipant;
    }

    public User getLeftChatParticipant() {
        return leftChatParticipant;
    }

    public String getNewChatTitle() {
        return newChatTitle;
    }

    public List<PhotoSize> getNewChatPhoto() {
        return newChatPhoto;
    }

    public boolean isDeleteChatPhoto() {
        return deleteChatPhoto;
    }

    public boolean isGroupChatCreated() {
        return groupChatCreated;
    }

    public String getCaption() {
        return caption;
    }

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

        else
            type = Type.UNKNOWN;
    }
}
