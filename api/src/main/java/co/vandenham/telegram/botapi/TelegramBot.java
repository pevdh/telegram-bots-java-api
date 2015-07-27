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

/**
 * Created by pieter on 24-7-15.
 */
abstract public class TelegramBot {

    private TelegramApi api;

    private Thread pollThread;
    private AtomicBoolean running = new AtomicBoolean();;
    private int lastUpdateId = 0;

    private ExecutorService executorService;

    public TelegramBot(String botToken) {
        api = new TelegramApi(botToken);
        executorService = Executors.newCachedThreadPool();
    }

    public void start() {
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

    public Message forwardMessage(int chatId, int fromChatId, int messageId) {
        return new ForwardMessageRequest(chatId, fromChatId, messageId).execute(api);
    }

    public User getMe() {
        return new GetMeRequest().execute(api);
    }

    public UserProfilePhotos getUserProfilePhotos(int userId) {
        return new GetUserProfilePhotosRequest(userId).execute(api);
    }

    public UserProfilePhotos getUserProfilePhotos(int userId, OptionalArgs optionalArgs) {
        return new GetUserProfilePhotosRequest(userId, optionalArgs).execute(api);
    }

    public Message sendAudio(int chatId, File audioFile) {
        return new SendAudioRequest(chatId, audioFile).execute(api);
    }

    public Message sendAudio(int chatId, File audioFile, OptionalArgs optionalArgs) {
        return new SendAudioRequest(chatId, audioFile, optionalArgs).execute(api);
    }

    public Message sendAudio(int chatId, String audioFileId) {
        return new SendAudioRequest(chatId, audioFileId).execute(api);
    }

    public Message sendAudio(int chatId, String audioFileId, OptionalArgs optionalArgs) {
        return new SendAudioRequest(chatId, audioFileId, optionalArgs).execute(api);
    }

    public Boolean sendChatAction(int chatId, ChatAction chatAction) {
        return new SendChatActionRequest(chatId, chatAction).execute(api);
    }

    public Message sendDocument(int chatId, File documentFile) {
        return new SendDocumentRequest(chatId, documentFile).execute(api);
    }

    public Message sendDocument(int chatId, File documentFile, OptionalArgs optionalArgs) {
        return new SendDocumentRequest(chatId, documentFile, optionalArgs).execute(api);
    }

    public Message sendDocument(int chatId, String documentFileId) {
        return new SendDocumentRequest(chatId, documentFileId).execute(api);
    }

    public Message sendDocument(int chatId, String documentFileId, OptionalArgs optionalArgs) {
        return new SendDocumentRequest(chatId, documentFileId, optionalArgs).execute(api);
    }

    public Message sendLocation(int chatId, float latitude, float longitude) {
        return new SendLocationRequest(chatId, latitude, longitude).execute(api);
    }

    public Message sendLocation(int chatId, float latitude, float longitude, OptionalArgs optionalArgs) {
        return new SendLocationRequest(chatId, latitude, longitude, optionalArgs).execute(api);
    }

    public Message sendMessage(int chatId, String text) {
        return new SendMessageRequest(chatId, text).execute(api);
    }

    public Message sendMessage(int chatId, String text, OptionalArgs optionalArgs) {
        return new SendMessageRequest(chatId, text, optionalArgs).execute(api);
    }

    public Message sendPhoto(int chatId, File photoFile) {
        return new SendPhotoRequest(chatId, photoFile).execute(api);
    }

    public Message sendPhoto(int chatId, File photoFile, OptionalArgs optionalArgs) {
        return new SendPhotoRequest(chatId, photoFile, optionalArgs).execute(api);
    }

    public Message sendPhoto(int chatId, String photoFileId) {
        return new SendPhotoRequest(chatId, photoFileId).execute(api);
    }

    public Message sendPhoto(int chatId, String photoFileId, OptionalArgs optionalArgs) {
        return new SendPhotoRequest(chatId, photoFileId, optionalArgs).execute(api);
    }

    public Message sendSticker(int chatId, File stickerFile) {
        return new SendStickerRequest(chatId, stickerFile).execute(api);
    }

    public Message sendSticker(int chatId, File stickerFile, OptionalArgs optionalArgs) {
        return new SendStickerRequest(chatId, stickerFile, optionalArgs).execute(api);
    }

    public Message sendSticker(int chatId, String stickerFileId) {
        return new SendStickerRequest(chatId, stickerFileId).execute(api);
    }

    public Message sendSticker(int chatId, String stickerFileId, OptionalArgs optionalArgs) {
        return new SendStickerRequest(chatId, stickerFileId, optionalArgs).execute(api);
    }

    public Message sendVideo(int chatId, File videoFile) {
        return new SendVideoRequest(chatId, videoFile).execute(api);
    }

    public Message sendVideo(int chatId, File videoFile, OptionalArgs optionalArgs) {
        return new SendVideoRequest(chatId, videoFile, optionalArgs).execute(api);
    }

    public Message sendVideo(int chatId, String videoFileId) {
        return new SendVideoRequest(chatId, videoFileId).execute(api);
    }

    public Message sendVideo(int chatId, String videoFileId, OptionalArgs optionalArgs) {
        return new SendVideoRequest(chatId, videoFileId, optionalArgs).execute(api);
    }

    abstract protected void onMessages(List<Message> messages);

    private class UpdatePoller implements Runnable {

        @Override
        public void run() {
            while (running.get()) {
                try {
                    poll();
                } catch (ApiException e) {
                    e.printStackTrace();
                    running.set(false);
                }
            }
        }

        public void poll() {
            OptionalArgs optionalArgs = new OptionalArgs().offset(lastUpdateId + 1).timeout(3);
            GetUpdatesRequest request = new GetUpdatesRequest(optionalArgs);

            List<Update> updates = request.execute(api);
            if (updates.size() > 0) {
                List<Message> newMessages = processUpdates(updates);
                notify(newMessages);
            }
        }

        private void notify(List<Message> newMessages) {
            executorService.submit(new MessageNotifier(newMessages));
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

    private class MessageNotifier implements Runnable {

        private List<Message> messages;

        public MessageNotifier(List<Message> messages) {
            this.messages = messages;
        }

        @Override
        public void run() {
            onMessages(messages);
        }
    }

}
