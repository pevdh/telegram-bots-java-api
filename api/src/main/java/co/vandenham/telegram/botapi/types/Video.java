package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object represents a video file.
 *
 * Any getters labeled <i>optional</i> might return a default value (such as {@code null}).
 *
 * @see <a href="https://core.telegram.org/bots/api#video">https://core.telegram.org/bots/api#video</a>
 */
public class Video {

    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("width")
    private int width;

    @JsonProperty("height")
    private int height;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("thumb")
    private PhotoSize thumb;

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
     * @return Video width as defined by sender
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Video height as defined by sender
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return Duration of the video in seconds as defined by sender
     */
    public int getDuration() {
        return duration;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Video thumbnail
     */
    public PhotoSize getThumb() {
        return thumb;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Mime type of a file as defined by sender
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * <i>Optional.</i>
     *
     * @return File size
     */
    public int getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Video{");
        sb.append("fileId='").append(fileId).append('\'');
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", duration=").append(duration);
        sb.append(", thumb=").append(thumb);
        sb.append(", mimeType='").append(mimeType).append('\'');
        sb.append(", fileSize=").append(fileSize);
        sb.append('}');
        return sb.toString();
    }
}
