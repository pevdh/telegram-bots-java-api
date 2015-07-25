package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import org.junit.Before;

/**
 * Created by pieter on 25-7-15.
 */
public class RequestTest {

    protected final static int CHAT_ID = 4663724;
    protected final static String TOKEN = "82565878:AAEBPQjeCPZgkCVrEVO7ewx4X7G7FV8JVRw";
    protected TelegramApi api;

    @Before
    public void setUp() throws Exception {
        api = new TelegramApi(TOKEN);

    }
}
