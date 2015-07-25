package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pieter on 25-7-15.
 */
public class Audio {

    @SerializedName("file_id")
    private String fileId;

    @SerializedName("duration")
    private int duration;

    @SerializedName("mime_type")
    private String mimeType;

    @SerializedName("file_size")
    private int fileSize;

    public Audio() {

    }

    public Audio(String fileId, int duration) {
        this.fileId = fileId;
        this.duration = duration;
    }

    public String getFileId() {
        return fileId;
    }

    public int getDuration() {
        return duration;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
}
