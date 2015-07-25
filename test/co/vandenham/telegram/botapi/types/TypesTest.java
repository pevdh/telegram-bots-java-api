package co.vandenham.telegram.botapi.types;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pieter on 25-7-15.
 */
public class TypesTest {

    private Gson gson;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
    }

    @Test
    public void testUser() {
        String json = "{\"id\":101176298,\"first_name\":\"RDSSBOT\",\"username\":\"rdss_bot\"}";
        User user = createSubject(json, User.class);
        assertEquals(101176298, user.getId());
        assertEquals("RDSSBOT", user.getFirstName());
        assertEquals("rdss_bot", user.getUsername());
    }

    @Test
    public void testGroupChat() {
        String json = "{\"id\":8926,\"title\":\"\\u5c4e\\u4f2f\\u98ef\\u98ef\\u4e4b\\u4ea4\"}";
        GroupChat groupChat = createSubject(json, GroupChat.class);
        assertEquals(groupChat.getId(), 8926);
    }

    @Test
    public void testDocument() {
        String json = "{\"file_name\":\"Text File\",\"thumb\":{},\"file_id\":\"BQADBQADMwIAAsYifgZ_CEh0u682xwI\",\"file_size\":446}";
        Document document = createSubject(json, Document.class);

        assertEquals("BQADBQADMwIAAsYifgZ_CEh0u682xwI", document.getFileId());
        assertEquals("Text File", document.getFileName());
    }

    @Test
    public void testUserProfilePhotos() {
        String json = "{\"total_count\":1,\"photos\":[[{\"file_id\":\"AgADAgADqacxG6wpRwABvEB6fpeIcKS4HAIkAATZH_SpyZjzIwdVAAIC\",\"file_size\":6150,\"width\":160,\"height\":160},{\"file_id\":\"AgADAgADqacxG6wpRwABvEB6fpeIcKS4HAIkAATOiTNi_YoJMghVAAIC\",\"file_size\":13363,\"width\":320,\"height\":320},{\"file_id\":\"AgADAgADqacxG6wpRwABvEB6fpeIcKS4HAIkAAQW4DyFv0-lhglVAAIC\",\"file_size\":28347,\"width\":640,\"height\":640},{\"file_id\":\"AgADAgADqacxG6wpRwABvEB6fpeIcKS4HAIkAAT50RvJCg0GQApVAAIC\",\"file_size\":33953,\"width\":800,\"height\":800}]]}";
        UserProfilePhotos upp = createSubject(json, UserProfilePhotos.class);
        assertEquals(160, upp.getPhotos().get(0).get(0).getWidth());
        assertEquals(800, upp.getPhotos().get(0).get(3).getHeight());
    }

    @Test
    public void testContact() {
        String json = "{\"phone_number\":\"00011111111\",\"first_name\":\"dd\",\"last_name\":\"ddl\",\"user_id\":8633}";
        Contact contact = createSubject(json, Contact.class);
        assertEquals("dd", contact.getFirstName());
        assertEquals("ddl", contact.getLastName());
    }

    @Test
    public void testMessageGroup() {
        String json = "{\"message_id\":10,\"from\":{\"id\":12345,\"first_name\":\"g\",\"last_name\":\"G\",\"username\":\"GG\"},\"chat\":{\"id\":-866,\"title\":\"\\u4ea4\"},\"date\":1435303157,\"text\":\"HIHI\"}";
        Message msg = createSubject(json, Message.class);
        assertEquals(Message.Type.TEXT, msg.getType());
        assertEquals("HIHI", msg.getText());
        assertTrue(msg.getChat().isGroupChat());
        assertEquals("GG", msg.getFrom().getUsername());
    }

    @Test
    public void testMessageAudio() {
        String json = "{\"message_id\":100,\"from\":{\"id\":10734,\"first_name\":\"dd\",\"last_name\":\"dd\",\"username\":\"dd\"},\"chat\":{\"id\":10734,\"first_name\":\"dd\",\"last_name\":\"dd\",\"username\":\"dd\"},\"date\":1435481343,\"audio\":{\"duration\":3,\"mime_type\":\"audio\\/ogg\",\"file_id\":\"ddg\",\"file_size\":8249}}";
        Message msg = createSubject(json, Message.class);
        assertEquals(Message.Type.AUDIO, msg.getType());
        assertEquals(3, msg.getAudio().getDuration());
    }

    @Test
    public void testMessageSticker() {
        String json = "{\"message_id\":98,\"from\":{\"id\":10734,\"first_name\":\"Fd\",\"last_name\":\"Wd\",\"username\":\"dd\"},\"chat\":{\"id\":10734,\"first_name\":\"Fd\",\"last_name\":\"Wd\",\"username\":\"dd\"},\"date\":1435479551,\"sticker\":{\"width\":550,\"height\":368,\"thumb\":{\"file_id\":\"AAQFABPJLB0sAAQq17w-li3bzoIfAAIC\",\"file_size\":1822,\"width\":90,\"height\":60},\"file_id\":\"BQADBQADNAIAAsYifgYdGJOa6bGAsQI\",\"file_size\":30320}}";
        Message msg = createSubject(json, Message.class);
        assertEquals(Message.Type.STICKER, msg.getType());
        assertEquals(368, msg.getSticker().getHeight());
        assertEquals(60, msg.getSticker().getThumb().getHeight());
    }

    @Test
    public void testMessageDocument() {
        String json = "{\"message_id\":97,\"from\":{\"id\":10734,\"first_name\":\"Fd\",\"last_name\":\"Wd\",\"username\":\"dd\"},\"chat\":{\"id\":10,\"first_name\":\"Fd\",\"last_name\":\"Wd\",\"username\":\"dd\"},\"date\":1435478744,\"document\":{\"file_name\":\"Text File\",\"thumb\":{},\"file_id\":\"BQADBQADMwIAAsYifgZ_CEh0u682xwI\",\"file_size\":446}}";
        Message msg = createSubject(json, Message.class);
        assertEquals(Message.Type.DOCUMENT, msg.getType());
        assertEquals("Text File", msg.getDocument().getFileName());
    }

    @Test
    public void testMessagePhoto() {
        String json = "{\"message_id\":96,\"from\":{\"id\":109734,\"first_name\":\"Fd\",\"last_name\":\"Wd\",\"username\":\"dd\"},\"chat\":{\"id\":10734,\"first_name\":\"Fd\",\"last_name\":\"dd\",\"username\":\"dd\"},\"date\":1435478191,\"photo\":[{\"file_id\":\"AgADBQADIagxG8YifgYv8yLSj76i-dd\",\"file_size\":615,\"width\":90,\"height\":67},{\"file_id\":\"AgADBQADIagxG8YifgYv8yLSj76i-dd\",\"file_size\":10174,\"width\":320,\"height\":240},{\"file_id\":\"dd-A_LsTIABFNx-FUOaEa_3AABAQABAg\",\"file_size\":53013,\"width\":759,\"height\":570}]}";
        Message msg = createSubject(json, Message.class);
        assertEquals(Message.Type.PHOTO, msg.getType());
        assertEquals(3, msg.getPhoto().size());
    }

    @Test
    public void testMessageVideo() {
        String json = "{\"message_id\":101,\"from\":{\"id\":109734,\"first_name\":\"dd\",\"last_name\":\"dd\",\"username\":\"dd\"},\"chat\":{\"id\":109734,\"first_name\":\"dd\",\"last_name\":\"dd\",\"username\":\"dd\"},\"date\":1435481960,\"video\":{\"duration\":3,\"caption\":\"\",\"width\":360,\"height\":640,\"thumb\":{\"file_id\":\"AAQFABPiYnBjkDwMAAIC\",\"file_size\":1597,\"width\":50,\"height\":90},\"file_id\":\"BAADBQADNifgb_TOPEKErGoQI\",\"file_size\":260699}}";
        Message msg = createSubject(json, Message.class);
        assertEquals(Message.Type.VIDEO, msg.getType());
        assertEquals(3, msg.getVideo().getDuration());
        assertEquals(50, msg.getVideo().getThumb().getWidth());
    }

    @Test
    public void testMessageLocation() {
        String json = "{\"message_id\":102,\"from\":{\"id\":108734,\"first_name\":\"dd\",\"last_name\":\"dd\",\"username\":\"dd\"},\"chat\":{\"id\":1089734,\"first_name\":\"dd\",\"last_name\":\"dd\",\"username\":\"dd\"},\"date\":1535482469,\"location\":{\"longitude\":127.479471,\"latitude\":26.090577}}";
        Message msg = createSubject(json, Message.class);
        assertEquals(Message.Type.LOCATION, msg.getType());
        assertTrue(msg.getLocation().getLatitude() > 26);
    }



    private <T> T createSubject(String json, Class<T> clsOf) {
        return gson.fromJson(json, clsOf);
    }

}