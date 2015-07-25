package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.User;

/**
 * Created by pieter on 24-7-15.
 */
public class GetMeRequest extends ApiRequest<User> {

    @Override
    public ApiResult<User> makeRequest(TelegramApi api) {
        String json = api.makeGetRequest("getMe");

        return deserialize(json, ResultTypes.USER);
    }

}
