package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object represents a sticker.
 *
 * Getters labeled <i>optional</i> might return a default value (such as {@code null}).
 *
 * @see <a href="https://core.telegram.org/bots/api#sticker">https://core.telegram.org/bots/api#sticker</a>
 */
public class Sticker extends TelegramType {

    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("width")
    private int width;

    @JsonProperty("height")
    private int height;

    @JsonProperty("thumb")
    private PhotoSize thumb;

    @JsonProperty("file_size")
    private int fileSize;

    /**
     * @return Unique identifier for this file
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @return Sticker width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Sticker height
     */
    public int getHeight() {
        return height;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Sticker thumbnail in .webp or .jpg format
     */
    public PhotoSize getThumb() {
        return thumb;
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
        final StringBuilder sb = new StringBuilder("Sticker{");
        sb.append("fileId='").append(fileId).append('\'');
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", thumb=").append(thumb);
        sb.append(", fileSize=").append(fileSize);
        sb.append('}');
        return sb.toString();
    }
}
