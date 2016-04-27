package co.vandenham.telegram.botapi.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Defines a general interface for reply markups.
 */
public abstract class ReplyMarkup {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    /**
     * Serializes this object to a JSON String.
     *
     * @return A JSON String representation of this object.
     */
    public String serialize() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

}
