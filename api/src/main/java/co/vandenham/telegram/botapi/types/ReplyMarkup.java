package co.vandenham.telegram.botapi.types;

/**
 * Defines a general interface for reply markups.
 */
public interface ReplyMarkup {

    /**
     * @return This instance converted to a JSON String.
     */
    String serialize();

}
