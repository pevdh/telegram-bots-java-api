package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.types.UserProfilePhotos;

import java.util.HashMap;
import java.util.Map;

public class GetUserProfilePhotosRequest implements ApiRequest<UserProfilePhotos> {

    private Map<String, String> args = new HashMap<>();

    public GetUserProfilePhotosRequest(int userId) {
        this(userId, null);
    }

    public GetUserProfilePhotosRequest(int userId, OptionalArgs optionalArgs) {
        args.put("user_id", String.valueOf(userId));

        if (optionalArgs != null)
            args.putAll(optionalArgs.options());
    }

    @Override
    public String getMethodName() {
        return "getUserProfilePhotos";
    }

    @Override
    public ResultTypes getResultType() {
        return ResultTypes.USER_PROFILE_PHOTOS;
    }

    @Override
    public Map<String, String> getArgs() {
        return args;
    }

    @Override
    public RequestStrategy getRequestStrategy() {
        return new PostStrategy();
    }

    @Override
    public String toString() {
        return "GetUserProfilePhotosRequest{" +
                "args=" + args +
                '}';
    }
}
