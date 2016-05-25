package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.Update;
import co.vandenham.telegram.botapi.types.User;
import co.vandenham.telegram.botapi.types.UserProfilePhotos;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

interface ApiRequest<T> {

    String getMethodName();

    ResultTypes getResultType();

    Map<String, String> getArgs();

    RequestStrategy getRequestStrategy();

    enum ResultTypes {
        USER(new TypeReference<ApiResult<User>>() {}),
        MESSAGE(new TypeReference<ApiResult<Message>>() {}),
        BOOLEAN(new TypeReference<ApiResult<Boolean>>() {}),
        USER_PROFILE_PHOTOS(new TypeReference<ApiResult<UserProfilePhotos>>() {}),
        LIST_OF_UPDATES(new TypeReference<ApiResult<List<Update>>>() {});

        private TypeReference<?> type;

        ResultTypes(TypeReference<?> type) {
            this.type = type;
        }

        public TypeReference<?> getType() {
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
