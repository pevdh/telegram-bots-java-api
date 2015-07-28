package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pieter on 25-7-15.
 */
public class Video {

    @SerializedName("file_id")
    private String fileId;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("duration")
    private int duration;

    @SerializedName("thumb")
    private PhotoSize thumb;

    @SerializedName("mime_type")
    private String mimeType;

    @SerializedName("file_size")
    private int fileSize;

    public Video() {
    }

    public Video(String fileId, int width, int height, int duration) {
        this.fileId = fileId;
        this.width = width;
        this.height = height;
        this.duration = duration;
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

    public int getDuration() {
        return duration;
    }

    public PhotoSize getThumb() {
        return thumb;
    }

    public String getMimeType() {
        return mimeType;
    }

    public int getFileSize() {
        return fileSize;
    }
}
