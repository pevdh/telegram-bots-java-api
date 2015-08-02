package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.requests.ApiResponse;
import co.vandenham.telegram.botapi.requests.ChatAction;
import co.vandenham.telegram.botapi.requests.OptionalArgs;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.User;
import co.vandenham.telegram.botapi.types.UserProfilePhotos;

import java.io.File;

public class ChatContext {
    
    private int chatId;
    private TelegramBot bot;
    private HandlerNotifier handlerNotifier;

    public ChatContext(int chatId, TelegramBot bot) {
        this.chatId = chatId;
        this.bot = bot;

        handlerNotifier = new HandlerNotifier(this);
    }

    public final void passMessage(Message message) {
        if (message.getChat().getId() != chatId)
            throw new IllegalArgumentException("A Message was passed to the wrong ChatContext.");

        onMessage(message);
        handlerNotifier.notifyHandlers(message);
    }

    public void onStart() {

    }

    public void onStop() {

    }

    protected void onMessage(Message message) {

    }

    public ApiResponse<Message> forwardMessage(int fromChatId, int toChatId, int messageId) {
        return bot.forwardMessage(toChatId, fromChatId, messageId);
    }

    public ApiResponse<User> getMe() {
        return bot.getMe();
    }

    public ApiResponse<UserProfilePhotos> getUserProfilePhotos(int userId) {
        return bot.getUserProfilePhotos(userId);
    }

    public ApiResponse<UserProfilePhotos> getUserProfilePhotos(int userId, OptionalArgs optionalArgs) {
        return bot.getUserProfilePhotos(userId, optionalArgs);
    }

    public ApiResponse<Message> sendAudio(File audioFile) {
        return bot.sendAudio(chatId, audioFile);
    }

    public ApiResponse<Message> sendAudio(File audioFile, OptionalArgs optionalArgs) {
        return bot.sendAudio(chatId, audioFile, optionalArgs);
    }

    public ApiResponse<Message> sendAudio(String audioFileId) {
        return bot.sendAudio(chatId, audioFileId);
    }

    public ApiResponse<Message> sendAudio(String audioFileId, OptionalArgs optionalArgs) {
        return bot.sendAudio(chatId, audioFileId, optionalArgs);
    }

    public ApiResponse<Boolean> sendChatAction(ChatAction chatAction) {
        return bot.sendChatAction(chatId, chatAction);
    }

    public ApiResponse<Message> sendDocument(File documentFile) {
        return bot.sendDocument(chatId, documentFile);
    }

    public ApiResponse<Message> sendDocument(File documentFile, OptionalArgs optionalArgs) {
        return bot.sendDocument(chatId, documentFile, optionalArgs);
    }

    public ApiResponse<Message> sendDocument(String documentFileId) {
        return bot.sendDocument(chatId, documentFileId);
    }

    public ApiResponse<Message> sendDocument(String documentFileId, OptionalArgs optionalArgs) {
        return bot.sendDocument(chatId, documentFileId, optionalArgs);
    }

    public ApiResponse<Message> sendLocation(float latitude, float longitude) {
        return bot.sendLocation(chatId, latitude, longitude);
    }

    public ApiResponse<Message> sendLocation(float latitude, float longitude, OptionalArgs optionalArgs) {
        return bot.sendLocation(chatId, latitude, longitude, optionalArgs);
    }

    public ApiResponse<Message> sendMessage(String text) {
        return bot.sendMessage(chatId, text);
    }

    public ApiResponse<Message> sendMessage(String text, OptionalArgs optionalArgs) {
        return bot.sendMessage(chatId, text, optionalArgs);
    }

    public ApiResponse<Message> sendPhoto(File photoFile) {
        return bot.sendPhoto(chatId, photoFile);
    }

    public ApiResponse<Message> sendPhoto(File photoFile, OptionalArgs optionalArgs) {
        return bot.sendPhoto(chatId, photoFile, optionalArgs);
    }

    public ApiResponse<Message> sendPhoto(String photoFileId) {
        return bot.sendPhoto(chatId, photoFileId);
    }

    public ApiResponse<Message> sendPhoto(String photoFileId, OptionalArgs optionalArgs) {
        return bot.sendPhoto(chatId, photoFileId, optionalArgs);
    }

    public ApiResponse<Message> sendSticker(File stickerFile) {
        return bot.sendSticker(chatId, stickerFile);
    }

    public ApiResponse<Message> sendSticker(File stickerFile, OptionalArgs optionalArgs) {
        return bot.sendSticker(chatId, stickerFile, optionalArgs);
    }

    public ApiResponse<Message> sendSticker(String stickerFileId) {
        return bot.sendSticker(chatId, stickerFileId);
    }

    public ApiResponse<Message> sendSticker(String stickerFileId, OptionalArgs optionalArgs) {
        return bot.sendSticker(chatId, stickerFileId, optionalArgs);
    }

    public ApiResponse<Message> sendVideo(File videoFile) {
        return bot.sendVideo(chatId, videoFile);
    }

    public ApiResponse<Message> sendVideo(File videoFile, OptionalArgs optionalArgs) {
        return bot.sendVideo(chatId, videoFile, optionalArgs);
    }

    public ApiResponse<Message> sendVideo(String videoFileId) {
        return bot.sendVideo(chatId, videoFileId);
    }

    public ApiResponse<Message> sendVideo(String videoFileId, OptionalArgs optionalArgs) {
        return bot.sendVideo(chatId, videoFileId, optionalArgs);
    }

    public ApiResponse<Message> sendVoice(int chatId, File voiceFile) {
        return bot.sendVoice(chatId, voiceFile);
    }

    public ApiResponse<Message> sendVoice(int chatId, File voiceFile, OptionalArgs optionalArgs) {
        return bot.sendVoice(chatId, voiceFile, optionalArgs);
    }

    public ApiResponse<Message> sendVoice(int chatId, String voiceFileId) {
        return bot.sendVoice(chatId, voiceFileId);
    }

    public ApiResponse<Message> sendVoice(int chatId, String voiceFileId, OptionalArgs optionalArgs) {
        return bot.sendVoice(chatId, voiceFileId, optionalArgs);
    }

    public ApiResponse<Message> replyTo(Message message, String text) {
        return bot.sendMessage(chatId, text, new OptionalArgs().replyToMessageId(message.getMessageId()));
    }

    public int getChatId() {
        return chatId;
    }
}
