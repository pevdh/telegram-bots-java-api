package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * This object represent a user's profile pictures.
 *
 * @see <a href="https://core.telegram.org/bots/api#userprofilephotos">https://core.telegram.org/bots/api#userprofilephotos</a>
 */
public class UserProfilePhotos {

    @JsonProperty("total_count")
    private int totalCount;

    @JsonProperty("photos")
    private List<List<PhotoSize>> photos;

    /**
     * @return Total number of profile pictures the target user has
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @return Requested profile pictures (in up to 4 sizes each)
     */
    public List<List<PhotoSize>> getPhotos() {
        return photos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserProfilePhotos{");
        sb.append("totalCount=").append(totalCount);
        sb.append(", photos=").append(photos);
        sb.append('}');
        return sb.toString();
    }
}
