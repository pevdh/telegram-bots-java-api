package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import org.junit.Before;

/**
 * Created by pieter on 25-7-15.
 */
public class RequestTest {

    protected final static int CHAT_ID = -1;
    protected final static String TOKEN = "TOKEN";
    protected TelegramApi api;

    @Before
    public void setUp() throws Exception {
        api = new TelegramApi(TOKEN);

    }
}
