package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.UserProfilePhotos;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class GetUserProfilePhotosRequest extends ApiRequest<UserProfilePhotos> {

    private int userId;
    private int offset;
    private int limit;

    private GetUserProfilePhotosRequest(Builder builder) {
        userId = builder.userId;
        offset = builder.offset;
        limit = builder.limit;
    }

    @Override
    protected ApiResult<UserProfilePhotos> makeRequest(TelegramApi api) {
        Map<String, String> args = new HashMap<>();
        args.put("user_id", String.valueOf(userId));

        if (offset != -1)
            args.put("offset", String.valueOf(offset));

        if (limit != -1)
            args.put("limit", String.valueOf(limit));

        String response = api.makePostRequest("getUserProfilePhotos", args);
        return deserialize(response, ResultTypes.USER_PROFILE_PHOTOS);
    }

    public static class Builder {

        private int userId;
        private int offset = -1;
        private int limit = -1;

        public Builder(int userId) {
            this.userId = userId;
        }

        public Builder setOffset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder setLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public GetUserProfilePhotosRequest build() {
            return new GetUserProfilePhotosRequest(this);
        }
    }
}
