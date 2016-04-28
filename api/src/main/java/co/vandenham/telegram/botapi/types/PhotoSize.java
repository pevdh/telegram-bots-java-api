package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object represents one size of a photo or a file / sticker thumbnail.
 *
 * Any getters labeled <i>optional</i> might return a default value (such as {@code null}).
 *
 * @see <a href="https://core.telegram.org/bots/api#photosize">https://core.telegram.org/bots/api#photosize</a>
 */
public class PhotoSize extends TelegramType {

    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("width")
    private int width;

    @JsonProperty("height")
    private int height;

    @JsonProperty("file_size")
    private int fileSize = -1;

    /**
     * @return Unique identifier for this file
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @return Photo width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Photo height
     */
    public int getHeight() {
        return height;
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
        final StringBuilder sb = new StringBuilder("PhotoSize{");
        sb.append("fileId='").append(fileId).append('\'');
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", fileSize=").append(fileSize);
        sb.append('}');
        return sb.toString();
    }
}
