package co.vandenham.telegram.botapi.requests;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

abstract public class ApiRequestExecutor {

    private static final Logger log = LoggerFactory.getLogger(ApiRequestExecutor.class);
    private static final Gson gson = new Gson();

    private static final ApiRequestExecutor synchronousExecutor = new SyncApiRequestExecutor();
    private static final ApiRequestExecutor asynchronousExecutor = new AsyncApiRequestExecutor();

    // Non instantiable/subclassable
    private ApiRequestExecutor() {
    }

    public static ApiRequestExecutor getSynchronousExecutor() {
        return synchronousExecutor;
    }

    public static ApiRequestExecutor getAsynchronousExecutor() {
        return asynchronousExecutor;
    }

    protected <T> ApiResult<T> deserialize(String json, ApiRequest.ResultTypes resultType) {
        return gson.fromJson(json, resultType.getType());
    }

    protected <T> T makeRequest(TelegramApi api, ApiRequest<T> request) {
        log.trace(request.toString());

        String response = request.getRequestStrategy().makeRequest(request, api);

        ApiResult<T> result = deserialize(response, request.getResultType());

        if (!result.isOk()) {
            log.error("Request {} failed with code {} ({})", request.getMethodName(), result.getErrorCode(), result.getDescription());
            throw new ApiException(request.getMethodName(), result);
        }

        return result.getResult();
    }

    abstract public <T> ApiResponse<T> execute(TelegramApi api, ApiRequest<T> request);

    private static class SyncApiResponse<T> implements ApiResponse<T> {

        private T result;

        public SyncApiResponse(T result) {
            this.result = result;
        }

        @Override
        public T getResult() {
            return result;
        }
    }

    private static class SyncApiRequestExecutor extends ApiRequestExecutor {

        public <T> ApiResponse<T> execute(TelegramApi api, ApiRequest<T> request) {
            return new SyncApiResponse<>(makeRequest(api, request));
        }

    }

    private static class AsyncApiResponse<T> implements ApiResponse<T> {

        private Future<T> result;

        public AsyncApiResponse(Future<T> result) {
            this.result = result;
        }

        @Override
        public T getResult() {
            try {
                return result.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Exception waiting for asynchronous result", e);
            }
            return null;
        }
    }

    private static class AsyncApiRequestExecutor extends ApiRequestExecutor {

        private static final ExecutorService executorService = Executors.newCachedThreadPool();

        @Override
        public <T> ApiResponse<T> execute(final TelegramApi api, final ApiRequest<T> request) {
            Future<T> future = executorService.submit(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    return makeRequest(api, request);
                }
            });

            return new AsyncApiResponse<>(future);
        }

    }


}
