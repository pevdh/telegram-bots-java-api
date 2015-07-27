package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.ApiException;
import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.Update;
import co.vandenham.telegram.botapi.types.User;
import co.vandenham.telegram.botapi.types.UserProfilePhotos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by pieter on 25-7-15.
 */
abstract public class ApiRequest<T> {

    private static final Logger log = Logger.getLogger(GetUpdatesRequest.class.getName());
    private static final Gson gson = new Gson();

    private  ApiResult<T> deserialize(String json, ResultTypes resultType) {
        return gson.fromJson(json, resultType.getType());
    }

    abstract protected String getMethodName();

    abstract protected ResultTypes getResultType();

    abstract protected Map<String, String> getArgs();

    abstract protected RequestStrategy getRequestStrategy();

    protected <K, V> void copyMap(Map<K, V> src, Map<K, V> dst) {
        dst.putAll(src);
    }

    public T execute(TelegramApi api) {
        log.info(toString());

        String response = getRequestStrategy().makeRequest(this, api);

        ApiResult<T> result = deserialize(response, getResultType());

        if (!result.isOk())
            throw new ApiException(getMethodName(), result);

        return result.getResult();
    }

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

    protected interface RequestStrategy {

        String makeRequest(ApiRequest<?> request, TelegramApi api);

    }

    protected final class PostStrategy implements RequestStrategy {

        @Override
        public String makeRequest(ApiRequest<?> request, TelegramApi api) {
            return api.makePostRequest(request.getMethodName(), request.getArgs());
        }

        @Override
        public String toString() {
            return "POST";
        }
    }

    protected final class GetStrategy implements RequestStrategy {

        @Override
        public String makeRequest(ApiRequest<?> request, TelegramApi api) {
            return api.makeGetRequest(request.getMethodName());
        }

        @Override
        public String toString() {
            return "GET";
        }
    }

    protected final class MultipartStrategy implements RequestStrategy {

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
