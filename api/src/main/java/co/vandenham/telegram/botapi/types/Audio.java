package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object represents an audio file (voice note).
 *
 * Any getters labeled <i>optional</i> might return a default value (such as {@code null}).
 *
 * @see <a href="https://core.telegram.org/bots/api#audio">https://core.telegram.org/bots/api#audio</a>
 */
public class Audio extends TelegramType {

    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("performer")
    private String performer;

    @JsonProperty("title")
    private String title;

    @JsonProperty("mime_type")
    private String mimeType;

    @JsonProperty("file_size")
    private int fileSize;

    /**
     * @return Unique identifier for this file
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @return Duration of the audio in seconds as defined by sender
     */
    public int getDuration() {
        return duration;
    }

    /**
     * <i>Optional.</i>
     *
     * @return MIME type of the file as defined by sender
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * <i>Optional.</i>
     *
     * @return The size of this audio file.
     */
    public int getFileSize() {
        return fileSize;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Performer of the audio as defined by sender or by audio tags
     */
    public String getPerformer() {
        return performer;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Title of the audio as defined by sender or by audio tags
     */
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Audio{");
        sb.append("fileId='").append(fileId).append('\'');
        sb.append(", duration=").append(duration);
        sb.append(", performer='").append(performer).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", mimeType='").append(mimeType).append('\'');
        sb.append(", fileSize=").append(fileSize);
        sb.append('}');
        return sb.toString();
    }
}
