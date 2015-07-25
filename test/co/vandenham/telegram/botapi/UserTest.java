package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.types.User;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pieter on 24-7-15.
 */
public class UserTest {

    @Test
    public void testFromJson() throws Exception {
        String json = "{\"id\":82565878,\"first_name\":\"SoulseekBot\",\"username\":\"SoulseekBot\"}";
        Gson gson = new Gson();

        User user = gson.fromJson(json, User.class);

        assertEquals(user.getId(), 82565878);
        assertEquals(user.getFirstName(), "SoulseekBot");
        assertEquals(user.getUsername(), "SoulseekBot");
    }


}