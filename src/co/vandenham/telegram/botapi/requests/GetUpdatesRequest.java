package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.TelegramApi;
import co.vandenham.telegram.botapi.types.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pieter on 25-7-15.
 */
public class GetUpdatesRequest extends ApiRequest<List<Update>> {

    private int offset;
    private int limit;
    private int timeout;

    private GetUpdatesRequest(Builder builder) {
        offset = builder.offset;
        limit = builder.limit;
        timeout = builder.timeout;
    }

    @Override
    protected ApiResult<List<Update>> makeRequest(TelegramApi api) {
        Map<String, String> args = new HashMap<>();

        if (offset != -1)
            args.put("offset", String.valueOf(offset));

        if (limit != -1)
            args.put("limit", String.valueOf(limit));

        if (timeout != -1)
            args.put("timeout", String.valueOf(timeout));

        String response = api.makePostRequest("getUpdates", args);

        return deserialize(response, ResultTypes.LIST_OF_UPDATES);
    }

    public static class Builder {

        private int offset;
        private int limit;
        private int timeout;

        public Builder() {
        }

        public Builder setOffset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder setLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public GetUpdatesRequest build() {
            return new GetUpdatesRequest(this);
        }
    }
}
