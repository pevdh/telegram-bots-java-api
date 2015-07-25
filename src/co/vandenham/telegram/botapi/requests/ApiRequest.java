package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.Update;
import co.vandenham.telegram.botapi.types.User;
import co.vandenham.telegram.botapi.types.UserProfilePhotos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by pieter on 25-7-15.
 */
abstract public class ApiRequest<T> {

    protected enum ResultTypes {
        USER(new TypeToken<ApiResult<User>>(){}.getType()),
        MESSAGE(new TypeToken<ApiResult<Message>>(){}.getType()),
        BOOLEAN(new TypeToken<ApiResult<Boolean>>(){}.getType()),
        USER_PROFILE_PHOTOS(new TypeToken<ApiResult<UserProfilePhotos>>(){}.getType()),
        LIST_OF_UPDATES(new TypeToken<ApiResult<List<Update>>>(){}.getType());

        private Type type;

        ResultTypes(Type type) {
            this.type = type;
        }

        public Type getType() {
            return type;
        }
    }
    
    private static final Gson gson = new Gson();
    
    protected  ApiResult<T> deserialize(String json, ResultTypes resultType) {
        return gson.fromJson(json, resultType.getType());
    }

    abstract protected ApiResult<T> makeRequest(TelegramApi api);

}
