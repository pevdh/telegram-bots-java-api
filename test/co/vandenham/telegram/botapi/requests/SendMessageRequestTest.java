package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.ReplyKeyboardMarkup;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pieter on 25-7-15.
 */
public class SendMessageRequestTest extends RequestTest {

    @Test
    public void testMakeRequest() throws Exception {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup.Builder().row("A", "B", "C").build();

        OptionalArgs optionalArgs = new OptionalArgs().replyMarkup(markup);
        SendMessageRequest request = new SendMessageRequest(CHAT_ID, "Hey", optionalArgs);

        Message message = request.execute(api);

        assertEquals(message.getText(), "Hey");
    }

}