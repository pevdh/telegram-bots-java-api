package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.Message;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by pieter on 25-7-15.
 */
public class SendPhotoRequestTest extends RequestTest {

    @Test
    public void testMakeRequest() throws Exception {
        File photo = new File("/home/pieter/Pictures/infoweb.png");

        OptionalArgs optionalArgs = new OptionalArgs().caption("Lol...");
        SendPhotoRequest request = new SendPhotoRequest(4663724, photo, optionalArgs);
        Message message = requestExecutor.execute(api, request).getResult();
        assertTrue(message.getMessageId() > 0);
    }
}