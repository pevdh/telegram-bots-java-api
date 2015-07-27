package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.requests.*;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.Update;
import co.vandenham.telegram.botapi.types.User;
import co.vandenham.telegram.botapi.types.UserProfilePhotos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by pieter on 24-7-15.
 */
abstract public class TelegramBot {

    private static final Logger logger = Logger.getLogger(TelegramBot.class.getName());

    private TelegramApi api;

    private Thread pollThread;
    private AtomicBoolean running = new AtomicBoolean();;
    private int lastUpdateId = 0;

    private ApiRequestExecutor requestExecutor;
    private ExecutorService executorService;

    public TelegramBot(String botToken) {
        api = new TelegramApi(botToken);
    }

    public void start() {
        logger.info("Starting");

        executorService = provideExecutorService();
        requestExecutor = shouldSendAsync() ?
                ApiRequestExecutor.getAsynchronousExecutor() : ApiRequestExecutor.getSynchronousExecutor();
        
        running.set(true);
        
        pollThread = new Thread(new UpdatePoller());
        pollThread.start();
    }

    public void stop() {
        running.set(false);

        try {
            pollThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    protected ExecutorService provideExecutorService() {
        return Executors.newCachedThreadPool();
    }

    protected boolean shouldSendAsync() {
        return true;
    }

    public ApiResponse<Message> forwardMessage(int chatId, int fromChatId, int messageId) {
        return requestExecutor.execute(api, new ForwardMessageRequest(chatId, fromChatId, messageId));
    }

    public ApiResponse<User> getMe() {
        return requestExecutor.execute(api, new GetMeRequest());
    }

    public ApiResponse<UserProfilePhotos> getUserProfilePhotos(int userId) {
        return requestExecutor.execute(api, new GetUserProfilePhotosRequest(userId));
    }

    public ApiResponse<UserProfilePhotos> getUserProfilePhotos(int userId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new GetUserProfilePhotosRequest(userId, optionalArgs));
    }

    public ApiResponse<Message> sendAudio(int chatId, File audioFile) {
        return requestExecutor.execute(api, new SendAudioRequest(chatId, audioFile));
    }

    public ApiResponse<Message> sendAudio(int chatId, File audioFile, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendAudioRequest(chatId, audioFile, optionalArgs));
    }

    public ApiResponse<Message> sendAudio(int chatId, String audioFileId) {
        return requestExecutor.execute(api, new SendAudioRequest(chatId, audioFileId));
    }

    public ApiResponse<Message> sendAudio(int chatId, String audioFileId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendAudioRequest(chatId, audioFileId, optionalArgs));
    }

    public ApiResponse<Boolean> sendChatAction(int chatId, ChatAction chatAction) {
        return requestExecutor.execute(api, new SendChatActionRequest(chatId, chatAction));
    }

    public ApiResponse<Message> sendDocument(int chatId, File documentFile) {
        return requestExecutor.execute(api, new SendDocumentRequest(chatId, documentFile));
    }

    public ApiResponse<Message> sendDocument(int chatId, File documentFile, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendDocumentRequest(chatId, documentFile, optionalArgs));
    }

    public ApiResponse<Message> sendDocument(int chatId, String documentFileId) {
        return requestExecutor.execute(api, new SendDocumentRequest(chatId, documentFileId));
    }

    public ApiResponse<Message> sendDocument(int chatId, String documentFileId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendDocumentRequest(chatId, documentFileId, optionalArgs));
    }

    public ApiResponse<Message> sendLocation(int chatId, float latitude, float longitude) {
        return requestExecutor.execute(api, new SendLocationRequest(chatId, latitude, longitude));
    }

    public ApiResponse<Message> sendLocation(int chatId, float latitude, float longitude, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendLocationRequest(chatId, latitude, longitude, optionalArgs));
    }

    public ApiResponse<Message> sendMessage(int chatId, String text) {
        return requestExecutor.execute(api, new SendMessageRequest(chatId, text));
    }

    public ApiResponse<Message> sendMessage(int chatId, String text, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendMessageRequest(chatId, text, optionalArgs));
    }

    public ApiResponse<Message> sendPhoto(int chatId, File photoFile) {
        return requestExecutor.execute(api, new SendPhotoRequest(chatId, photoFile));
    }

    public ApiResponse<Message> sendPhoto(int chatId, File photoFile, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendPhotoRequest(chatId, photoFile, optionalArgs));
    }

    public ApiResponse<Message> sendPhoto(int chatId, String photoFileId) {
        return requestExecutor.execute(api, new SendPhotoRequest(chatId, photoFileId));
    }

    public ApiResponse<Message> sendPhoto(int chatId, String photoFileId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendPhotoRequest(chatId, photoFileId, optionalArgs));
    }

    public ApiResponse<Message> sendSticker(int chatId, File stickerFile) {
        return requestExecutor.execute(api, new SendStickerRequest(chatId, stickerFile));
    }

    public ApiResponse<Message> sendSticker(int chatId, File stickerFile, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendStickerRequest(chatId, stickerFile, optionalArgs));
    }

    public ApiResponse<Message> sendSticker(int chatId, String stickerFileId) {
        return requestExecutor.execute(api, new SendStickerRequest(chatId, stickerFileId));
    }

    public ApiResponse<Message> sendSticker(int chatId, String stickerFileId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendStickerRequest(chatId, stickerFileId, optionalArgs));
    }

    public ApiResponse<Message> sendVideo(int chatId, File videoFile) {
        return requestExecutor.execute(api, new SendVideoRequest(chatId, videoFile));
    }

    public ApiResponse<Message> sendVideo(int chatId, File videoFile, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendVideoRequest(chatId, videoFile, optionalArgs));
    }

    public ApiResponse<Message> sendVideo(int chatId, String videoFileId) {
        return requestExecutor.execute(api, new SendVideoRequest(chatId, videoFileId));
    }

    public ApiResponse<Message> sendVideo(int chatId, String videoFileId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendVideoRequest(chatId, videoFileId, optionalArgs));
    }

    protected ExecutorService getExecutorService() {
        return executorService;
    }

    protected void onMessage(Message message) {
        // Can be overridden by subclasses.
    };

    protected void notifyNewMessages(List<Message> messages) {
        for (final Message message : messages) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    onMessage(message);
                }
            });
        }
    }

    private class UpdatePoller implements Runnable {

        @Override
        public void run() {
            while (running.get()) {
                try {
                    poll();
                } catch (ApiException e) {
                    logger.log(Level.SEVERE, "An exception occurred while polling Telegram.", e);
                    running.set(false);
                }
            }
        }

        public void poll() {
            OptionalArgs optionalArgs = new OptionalArgs().offset(lastUpdateId + 1).timeout(3);
            GetUpdatesRequest request = new GetUpdatesRequest(optionalArgs);

            List<Update> updates = requestExecutor.execute(api, request).getResult();
            if (updates.size() > 0) {
                List<Message> newMessages = processUpdates(updates);
                notifyNewMessages(newMessages);
            }
        }

        private List<Message> processUpdates(List<Update> updates) {
            List<Message> newMessages = new ArrayList<Message>();

            for (Update update : updates) {
                if (update.getUpdateId() > lastUpdateId)
                    lastUpdateId = update.getUpdateId();
                newMessages.add(update.getMessage());
            }

            return newMessages;
        }
    }

}
