package co.vandenham.telegram.botapi.requests;

import co.vandenham.telegram.botapi.ApiException;
import com.google.gson.Gson;

import java.util.concurrent.*;
import java.util.logging.Logger;

abstract public class ApiRequestExecutor {

    private static final Logger log = Logger.getLogger(ApiRequestExecutor.class.getName());
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
        log.info(request.toString());

        String response = request.getRequestStrategy().makeRequest(request, api);

        ApiResult<T> result = deserialize(response, request.getResultType());

        if (!result.isOk())
            throw new ApiException(request.getMethodName(), result);

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
                e.printStackTrace();
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
