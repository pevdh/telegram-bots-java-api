package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pieter on 25-7-15.
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

    public Document() {
    }

    public Document(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public PhotoSize getThumb() {
        return thumb;
    }


    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public int getFileSize() {
        return fileSize;
    }

}
