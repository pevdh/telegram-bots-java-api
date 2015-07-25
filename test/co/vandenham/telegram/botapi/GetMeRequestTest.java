package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.requests.ApiResult;
import co.vandenham.telegram.botapi.requests.GetMeRequest;
import co.vandenham.telegram.botapi.types.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pieter on 25-7-15.
 */
public class GetMeRequestTest {

    private TelegramApi api;

    @Before
    public void setUp() throws Exception {
        api = new TelegramApi("82565878:AAEBPQjeCPZgkCVrEVO7ewx4X7G7FV8JVRw");
    }

    @Test
    public void testMakeRequest() throws Exception {
        ApiResult<User> result = new GetMeRequest().makeRequest(api);
        assertEquals(result.getResult().getUsername(), "SoulseekBot");
    }
}