package co.vandenham.telegram.botapi;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by pieter on 24-7-15.
 */
public class TelegramApiTest {

    @Test
    public void makeRequestShouldWork() {
        String response = TelegramApi.makeGetRequest("82565878:AAEBPQjeCPZgkCVrEVO7ewx4X7G7FV8JVRw", "getMe");
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    public void createQueryStringShouldWork() throws UnsupportedEncodingException {
        Map<String, String> arguments = new HashMap<>(2);
        arguments.put("email", "monkey@123.com");
        arguments.put("redirection", "true");
        String queryString = TelegramApi.createQueryString(arguments);
        String expected = "redirection=true&email=" + URLEncoder.encode("monkey@123.com", "UTF-8");
        assertEquals(expected, queryString);
    }

    @Test
    public void makePostRequestShouldWork() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("chat_id", "4663724");
        arguments.put("text", "test123");

        String response =
                TelegramApi.makePostRequest("82565878:AAEBPQjeCPZgkCVrEVO7ewx4X7G7FV8JVRw", "sendMessage", arguments);
        System.out.println(response);
        assertNotNull(response);
    }

}