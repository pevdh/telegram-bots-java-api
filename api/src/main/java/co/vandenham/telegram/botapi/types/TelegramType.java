package co.vandenham.telegram.botapi.types;

import org.slf4j.Logger;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.slf4j.LoggerFactory;

/**
 * Created by fedmest on 28/04/2016.
 */
public abstract class TelegramType {
    private static final Logger log = LoggerFactory.getLogger(TelegramType.class);

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        log.warn("Found unhandled property '{}' value '{}' in {}", key, value, toString());
    }
}
