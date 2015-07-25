package co.vandenham.telegram.botapi.types;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pieter on 25-7-15.
 */
public class UserProfilePhotos {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("photos")
    private List<List<PhotoSize>> photos;

    public UserProfilePhotos() {
    }

    public UserProfilePhotos(int totalCount, List<List<PhotoSize>> photos) {
        this.totalCount = totalCount;
        this.photos = photos;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<List<PhotoSize>> getPhotos() {
        return photos;
    }
}
