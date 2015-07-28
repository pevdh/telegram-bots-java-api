package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * This object represents a sticker.
 *
 * Getters labeled <i>optional</i> might return a default value (such as {@code null}).
 *
 * @see <a href="https://core.telegram.org/bots/api#sticker">https://core.telegram.org/bots/api#sticker</a>
 */
public class Sticker {

    @SerializedName("file_id")
    private String fileId;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("thumb")
    private PhotoSize thumb;

    @SerializedName("file_size")
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
