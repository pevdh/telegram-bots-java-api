package co.vandenham.telegram.botapi.requests;

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
        File photo = new File(getClass().getClassLoader().getResource("logo.png").getPath());

        OptionalArgs optionalArgs = new OptionalArgs().caption("Sent you a photo...");
        SendPhotoRequest request = new SendPhotoRequest(4663724, photo, optionalArgs);
        Message message = requestExecutor.execute(api, request).getResult();
        assertTrue(message.getMessageId() > 0);
    }
}