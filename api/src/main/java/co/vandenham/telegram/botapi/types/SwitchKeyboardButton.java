package co.vandenham.telegram.botapi.types;

/**
 * Created by fedmest on 22/04/2016.
 */
public class SwitchKeyboardButton extends InlineKeyboardButton {

    public SwitchKeyboardButton(String text, String query) {
        super(text);
        this.query = query;
    }

}
