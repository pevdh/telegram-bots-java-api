package co.vandenham.telegram.botapi;

import org.junit.Before;
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

    private TelegramApi api;

    @Before
    public void setUp() throws Exception {
        api = new TelegramApi("82565878:AAEBPQjeCPZgkCVrEVO7ewx4X7G7FV8JVRw");

    }

    @Test
    public void makeRequestShouldWork() {
        String response = api.makeGetRequest("getMe");
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    public void makePostRequestShouldWork() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("chat_id", "4663724");
        arguments.put("text", "test123");

        String response =
                api.makePostRequest("sendMessage", arguments);
        System.out.println(response);
        assertNotNull(response);
    }

}