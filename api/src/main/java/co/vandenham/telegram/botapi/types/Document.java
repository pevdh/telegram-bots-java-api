package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * This object represents a general file (as opposed to photos ({@link PhotoSize}) and audio ({@link Audio}) files).
 *
 * Any getters labeled <i>optional</i> might return a default value (such as {@code null}).
 *
 * @see <a href="https://core.telegram.org/bots/api#document>https://core.telegram.org/bots/api#document</a>
 */
public class Document {

    @SerializedName("file_id")
    private String fileId;

    @SerializedName("thumb")
    private PhotoSize thumb;

    @SerializedName("file_name")
    private String fileName;

    @SerializedName("mime_type")
    private String mimeType;

    @SerializedName("file_size")
    private int fileSize;

    /**
     * @return Unique file identifier
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Document thumbnail as defined by sender
     */
    public PhotoSize getThumb() {
        return thumb;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Original filename as defined by sender
     */
    public String getFileName() {
        return fileName;
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
     * @return File size of this document
     */
    public int getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Document{");
        sb.append("fileId='").append(fileId).append('\'');
        sb.append(", thumb=").append(thumb);
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", mimeType='").append(mimeType).append('\'');
        sb.append(", fileSize=").append(fileSize);
        sb.append('}');
        return sb.toString();
    }
}
