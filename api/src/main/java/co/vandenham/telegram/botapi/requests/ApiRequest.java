package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.Update;
import co.vandenham.telegram.botapi.types.User;
import co.vandenham.telegram.botapi.types.UserProfilePhotos;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by pieter on 27-7-15.
 */
public interface ApiRequest<T> {

    String getMethodName();

    ResultTypes getResultType();

    Map<String, String> getArgs();

    RequestStrategy getRequestStrategy();

    enum ResultTypes {
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

    interface RequestStrategy {

        String makeRequest(ApiRequest<?> request, TelegramApi api);

    }

    final class PostStrategy implements RequestStrategy {

        @Override
        public String makeRequest(ApiRequest<?> request, TelegramApi api) {
            return api.makePostRequest(request.getMethodName(), request.getArgs());
        }

        @Override
        public String toString() {
            return "POST";
        }
    }

    final class GetStrategy implements RequestStrategy {

        @Override
        public String makeRequest(ApiRequest<?> request, TelegramApi api) {
            return api.makeGetRequest(request.getMethodName());
        }

        @Override
        public String toString() {
            return "GET";
        }
    }

    final class MultipartStrategy implements RequestStrategy {

        private File file;
        private String fieldName;

        public MultipartStrategy(File file, String fieldName) {
            this.file = file;
            this.fieldName = fieldName;
        }

        @Override
        public String makeRequest(ApiRequest<?> request, TelegramApi api) {
            return api.makeMultipartRequest(request.getMethodName(), request.getArgs(), fieldName, file);
        }

        @Override
        public String toString() {
            return "MULTIPART";
        }
    }

}
