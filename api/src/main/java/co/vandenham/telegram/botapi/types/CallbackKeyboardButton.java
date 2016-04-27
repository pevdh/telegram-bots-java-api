package co.vandenham.telegram.botapi.types;

/**
 * Created by fedmest on 22/04/2016.
 */
public class CallbackKeyboardButton extends InlineKeyboardButton {

    public CallbackKeyboardButton(String text, String data) {
        super(text);
        // TODO data max 64 bytes
        this.data = data;
    }

}
