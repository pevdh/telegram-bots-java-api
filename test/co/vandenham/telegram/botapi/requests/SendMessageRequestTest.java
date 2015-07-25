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

        SendMessageRequest request = new SendMessageRequest.Builder(CHAT_ID, "Hey")
                .setReplyMarkup(markup)
                .build();

        ApiResult<Message> result = request.makeRequest(api);

        assertEquals(result.getResult().getText(), "Hey");
    }

}