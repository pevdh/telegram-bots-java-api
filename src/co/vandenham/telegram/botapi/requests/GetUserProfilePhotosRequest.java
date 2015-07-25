package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.UserProfilePhotos;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class GetUserProfilePhotosRequest extends ApiRequest<UserProfilePhotos> {

    private Map<String, String> args = new HashMap<>();

    public GetUserProfilePhotosRequest(int userId) {
        this(userId, null);
    }

    public GetUserProfilePhotosRequest(int userId, OptionalArgs optionalArgs) {
        args.put("user_id", String.valueOf(userId));

        if (optionalArgs != null)
            copyMap(optionalArgs.getOptions(), args);
    }

    @Override
    protected String getMethodName() {
        return "getUserProfilePhotos";
    }

    @Override
    protected ResultTypes getResultType() {
        return ResultTypes.USER_PROFILE_PHOTOS;
    }

    @Override
    protected Map<String, String> getArgs() {
        return args;
    }

    @Override
    protected RequestStrategy getRequestStrategy() {
        return new PostStrategy();
    }

    @Override
    public String toString() {
        return "GetUserProfilePhotosRequest{" +
                "args=" + args +
                '}';
    }
}
