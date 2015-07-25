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

        SendPhotoRequest request = new SendPhotoRequest.Builder(4663724, photo).setCaption("Lol...").build();
        ApiResult<Message> result = request.makeRequest(api);
        assertTrue(result.isOk());
    }
}