package co.vandenham.telegram.botapi.types;

/**
 * Created by fedmest on 22/04/2016.
 */
public class UrlKeyboardButton extends InlineKeyboardButton {

    public UrlKeyboardButton(String text, String url) {
        super(text);
        this.url = url;
    }

}
