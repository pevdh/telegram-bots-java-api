package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pieter on 25-7-15.
 */
public class PhotoSize {

    @SerializedName("file_id")
    private String fileId;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("file_size")
    private int fileSize = -1;

    public PhotoSize() {
    }

    public PhotoSize(String fileId, int width, int height) {
        this.fileId = fileId;
        this.width = width;
        this.height = height;
    }

    public String getFileId() {
        return fileId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
}
