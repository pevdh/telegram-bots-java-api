package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.requests.ApiResult;
import co.vandenham.telegram.botapi.requests.GetMeRequest;
import co.vandenham.telegram.botapi.requests.RequestTest;
import co.vandenham.telegram.botapi.types.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pieter on 25-7-15.
 */
public class GetMeRequestTest extends RequestTest {

    @Test
    public void testMakeRequest() throws Exception {
        User user = new GetMeRequest().execute(api);
        assertEquals(user.getUsername(), "SoulseekBot");
    }
}