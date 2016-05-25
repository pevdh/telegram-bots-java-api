package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object represents a phone contact.
 *
 * Any getters labeled <i>optional</i> might return a default value (such as {@code null}).
 *
 * @see <a href="https://core.telegram.org/bots/api#contact">https://core.telegram.org/bots/api#contact</a>
 */
public class Contact extends TelegramType {

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("user_id")
    private int userId;

    /**
     * @return Contact's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return Contact's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Contact's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * <i>Optional.</i>
     *
     * @return Contact's user identifier in Telegram
     */
    public int getUserId() {
        return userId;
    }

}
