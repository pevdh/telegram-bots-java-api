package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object represents a point on the map.
 *
 * @see <a href="https://core.telegram.org/bots/api#location">https://core.telegram.org/bots/api#location</a>
 */
public class Location extends TelegramType {

    @JsonProperty("longitude")
    private float longitude;

    @JsonProperty("latitude")
    private float latitude;

    /**
     * @return Longitude as defined by sender
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @return Latitude as defined by sender
     */
    public float getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Location{");
        sb.append("longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append('}');
        return sb.toString();
    }
}
