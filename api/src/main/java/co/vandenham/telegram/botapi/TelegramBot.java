package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.requests.*;
import co.vandenham.telegram.botapi.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class represents a TelegramBot.
 * <p/>
 * To use it, use any of the available subclasses or subclass it yourself.
 */
abstract public class TelegramBot {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    private TelegramApi api;

    private Thread pollThread;
    private AtomicBoolean running = new AtomicBoolean();
    private int lastUpdateId = 0;

    private ApiRequestExecutor requestExecutor;
    private ExecutorService executorService;

    private HandlerNotifier handlerNotifier;
    private boolean sendAsync = true;


    /**
     * Convenience constructor for {@code TelegramBot(botToken, true)}
     */
    public TelegramBot(String botToken) {
        this(botToken, true);
    }

    /**
     * Constructs a TelegramBot using the provided {@code botToken}, If {@code sendAsync} is {@code true},
     * the bot invokes all {@code sendXXX} methods asynchronously.
     *
     * @param botToken  The token provided by @BotFather
     * @param sendAsync Whether this bot should invoke {@code sendXXX} methods asynchronously.
     */
    public TelegramBot(String botToken, boolean sendAsync) {
        api = new TelegramApi(botToken);
        handlerNotifier = new HandlerNotifier(this);
        this.sendAsync = sendAsync;
    }

    /**
     * Check whether the bot is running by looking at the state of its polling thread as well as the running variable
     *
     */
    public boolean isRunning() {
        logger.trace("Running state: polling thread {} alive {}, running flag {}", pollThread, pollThread != null && pollThread.isAlive(), running.get());
        return (pollThread != null) && (running.get() == true) && pollThread.isAlive();
    }

    /**
     * Starts the bot.
     * <p/>
     * First, it instantiates a {@link java.util.concurrent.ExecutorService} by calling {@link TelegramBot#provideExecutorService()}.
     * If this instance is constructed with {@code sendAsync} set to {@code true}, it instantiates a asynchronous {@link ApiRequestExecutor},
     * otherwise a synchronous version is used.
     * <p/>
     * After this, a polling {@link java.lang.Thread} is instantiated and the bot starts polling the Telegram API.
     */
    public final void start() {
        logger.info("Starting telegram bot...");
        if (running.get()) {
            logger.debug("Trying to start bot, but it seems it's already started");
            logger.trace("If you expected the bot to be down, maybe the thread has crashed");
            logger.trace("Issue a stop() followed by a start() to reset the internal state");
            logger.trace("Running state: polling thread {} alive {}, running flag {}", pollThread, pollThread != null && pollThread.isAlive(), running.get());
        } else {
            executorService = provideExecutorService();
            requestExecutor = sendAsync ?
                    ApiRequestExecutor.getAsynchronousExecutor() : ApiRequestExecutor.getSynchronousExecutor();

            running.set(true);

            pollThread = new Thread(new UpdatePoller());
            pollThread.start();
            onStart();
        }
        logger.info("Telegram bot started...");
    }

    protected void onStart() {

    }

    /**
     * Stops the bot and joins the polling {@link Thread}.
     */
    public final void stop() {
        logger.info("Stopping telegram bot...");
        running.set(false);

        try {
            if (pollThread != null && pollThread.isAlive()) {
                pollThread.join();
                onStop();
            } else {
                logger.debug("Trying to stop bot, but it's not running");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Telegram bot stopped.");
    }

    protected void onStop() {

    }

    /**
     * Instantiates and returns an {@link ExecutorService}.
     * <p/>
     * By default, {@link java.util.concurrent.Executors#newCachedThreadPool()} is used.
     * This method can safely be overridden to adjust this behaviour.
     * This method can safely be overridden to return null, but if you decide to do so, {@link TelegramBot#notifyNewUpdates(List)}
     * <b>must</b> be overridden to avoid a NPE.
     *
     * @return An instantiated {@link ExecutorService}
     */
    protected ExecutorService provideExecutorService() {
        return Executors.newCachedThreadPool();
    }

    /**
     * Forwards a message with ID {@code messageId} from {@code fromChatId} to {@code chatId}.
     *
     * @param chatId     Unique identifier for the message recipient — User or GroupChat id
     * @param fromChatId Unique identifier for the chat where the original message was sent — User or GroupChat id
     * @param messageId  Unique message identifier
     * @return An {@code ApiResponse} with the sent {@link Message}
     * @see <a href="https://core.telegram.org/bots/api#forwardmessage">https://core.telegram.org/bots/api#forwardmessage</a>
     */
    public final ApiResponse<Message> forwardMessage(int chatId, int fromChatId, int messageId) {
        return requestExecutor.execute(api, new ForwardMessageRequest(chatId, fromChatId, messageId));
    }

    /**
     * A simple method for testing your bot's auth token. Requires no parameters. Returns basic information about the bot in form of a User object.
     *
     * @return this bot's information, in the form of a {@code User} wrapped in a {@code ApiResponse}
     * @see <a href="https://core.telegram.org/bots/api#getme">https://core.telegram.org/bots/api#getme</a>
     */
    public final ApiResponse<User> getMe() {
        return requestExecutor.execute(api, new GetMeRequest());
    }

    /**
     * @see TelegramBot#getUserProfilePhotos(int, OptionalArgs)
     */
    public final ApiResponse<UserProfilePhotos> getUserProfilePhotos(int userId) {
        return requestExecutor.execute(api, new GetUserProfilePhotosRequest(userId));
    }

    /**
     * Returns a {@link UserProfilePhotos} for a user.
     * <p/>
     * For any optional arguments, refer to the Telegram documentation.
     *
     * @param userId       Unique identifier of the target user
     * @param optionalArgs Any optional arguments for this method
     * @return The requested {@link UserProfilePhotos}
     * @see <a href="https://core.telegram.org/bots/api#getuserprofilephotos">https://core.telegram.org/bots/api#getuserprofilephotos</a>
     * @see OptionalArgs
     */
    public final ApiResponse<UserProfilePhotos> getUserProfilePhotos(int userId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new GetUserProfilePhotosRequest(userId, optionalArgs));
    }

    /**
     * @see TelegramBot#sendAudio(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendAudio(int chatId, File audioFile) {
        return requestExecutor.execute(api, new SendAudioRequest(chatId, audioFile));
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .mp3 format. On success, the sent Message is returned. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
     *
     * For backward compatibility, when the fields title and performer are both empty and the mime-type of the file
     * to be sent is not audio/mpeg, the file will be sent as a playable voice message.
     * For this to work, the audio must be in an .ogg file encoded with OPUS.
     * This behavior will be phased out in the future. For sending voice messages, use the sendVoice method instead.
     *
     * For any optional arguments, refer to the Telegram documentation.
     *
     * @param chatId       Unique identifier for the message recipient - {@link User} or {@link co.vandenham.telegram.botapi.types.GroupChat} id
     * @param audioFile    Audio {@link File} to send
     * @param optionalArgs Any optional arguments for this method
     * @return The sent {@link Message}
     * @see <a href="https://core.telegram.org/bots/api#sendaudio">https://core.telegram.org/bots/api#sendaudio</a>
     */
    public final ApiResponse<Message> sendAudio(int chatId, File audioFile, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendAudioRequest(chatId, audioFile, optionalArgs));
    }

    /**
     * @see TelegramBot#sendAudio(int, String, OptionalArgs)
     */
    public final ApiResponse<Message> sendAudio(int chatId, String audioFileId) {
        return requestExecutor.execute(api, new SendAudioRequest(chatId, audioFileId));
    }

    /**
     * This version takes a file id instead of a {@link File}, as a {@code String} argument.
     *
     * @see TelegramBot#sendAudio(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendAudio(int chatId, String audioFileId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendAudioRequest(chatId, audioFileId, optionalArgs));
    }

    /**
     * Use this method when you need to tell the user that something is happening on the bot's side.
     * The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status).
     *
     * @param chatId     Unique identifier for the message recipient - {@link User} or {@link co.vandenham.telegram.botapi.types.GroupChat} id.
     * @param chatAction The target {@link ChatAction}
     * @return True if the request was successful
     * @see <a href="https://core.telegram.org/bots/api#sendchataction">https://core.telegram.org/bots/api#sendchataction</a>
     */
    public final ApiResponse<Boolean> sendChatAction(int chatId, ChatAction chatAction) {
        return requestExecutor.execute(api, new SendChatActionRequest(chatId, chatAction));
    }

    /**
     * @see TelegramBot#sendAudio(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendDocument(int chatId, File documentFile) {
        return requestExecutor.execute(api, new SendDocumentRequest(chatId, documentFile));
    }

    /**
     * Use this method to send general files. On success, the sent Message is returned.
     * Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
     * <p/>
     * For any optional arguments, refer to the Telegram documentation.
     *
     * @param chatId       Unique identifier for the message recipient - {@link User} or {@link co.vandenham.telegram.botapi.types.GroupChat} id
     * @param documentFile File to send
     * @param optionalArgs Any optional arguments
     * @return The sent {@link Message}
     * @see <a href="https://core.telegram.org/bots/api#senddocument">https://core.telegram.org/bots/api#senddocument</a>
     */
    public final ApiResponse<Message> sendDocument(int chatId, File documentFile, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendDocumentRequest(chatId, documentFile, optionalArgs));
    }

    /**
     * @see TelegramBot#sendDocument(int, String, OptionalArgs)
     */
    public final ApiResponse<Message> sendDocument(int chatId, String documentFileId) {
        return requestExecutor.execute(api, new SendDocumentRequest(chatId, documentFileId));
    }

    /**
     * This version takes a file id instead of a {@link File}.
     *
     * @see TelegramBot#sendDocument(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendDocument(int chatId, String documentFileId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendDocumentRequest(chatId, documentFileId, optionalArgs));
    }

    /**
     * @see TelegramBot#sendLocation(int, float, float, OptionalArgs)
     */
    public final ApiResponse<Message> sendLocation(int chatId, float latitude, float longitude) {
        return requestExecutor.execute(api, new SendLocationRequest(chatId, latitude, longitude));
    }

    /**
     * Use this method to send point on the map.
     * <p/>
     * For any optional arguments, refer to the Telegram documentation.
     *
     * @param chatId       Unique identifier for the message recipient - {@link User} or {@link co.vandenham.telegram.botapi.types.GroupChat} id
     * @param latitude     Latitude of location
     * @param longitude    Longitude of location
     * @param optionalArgs Any optional arguments
     * @return the sent {@link Message}
     * @see <a href="https://core.telegram.org/bots/api#sendlocation">https://core.telegram.org/bots/api#sendlocation</a>
     */
    public final ApiResponse<Message> sendLocation(int chatId, float latitude, float longitude, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendLocationRequest(chatId, latitude, longitude, optionalArgs));
    }

    /**
     * @see TelegramBot#sendMessage(int, String, OptionalArgs)
     */
    public final ApiResponse<Message> sendMessage(int chatId, String text) {
        return requestExecutor.execute(api, new SendMessageRequest(chatId, text));
    }

    /**
     * Use this method to send answers to callback queries sent from inline keyboards. The answer will be displayed to
     * the user as a notification at the top of the chat screen or as an alert. On success, True is returned.
     *
     * @param callbackId   Unique identifier for the query to be answered
     * @param text         Text of the notification. If not specified, nothing will be shown to the user
     * @param showAlert    If true, an alert will be shown by the client instead of a notification at the top of the chat screen. Defaults to false.
     * @return true on success
     * @see <a href="https://core.telegram.org/bots/api#answercallbackquery">the Telegram Bot API</a> for more information.
     */
    public final ApiResponse<Boolean> answerCallbackQuery(String callbackId, String text, boolean showAlert) {
        return requestExecutor.execute(api, new AnswerCallbackQueryRequest(callbackId, text, showAlert));
    }

    /**
     * @see TelegramBot#answerCallbackQuery(String, String, boolean)
     */
    public final ApiResponse<Boolean> answerCallbackQuery(CallbackQuery callback) {
        return answerCallbackQuery(callback.getId(), null, false);
    }

    /**
     * @see TelegramBot#answerCallbackQuery(String, String, boolean)
     */
    public final ApiResponse<Boolean> answerCallbackQuery(CallbackQuery callback, String text) {
        return answerCallbackQuery(callback.getId(), text, false);
    }

    /**
     * @see TelegramBot#answerCallbackQuery(String, String, boolean)
     */
    public final ApiResponse<Boolean> alertCallbackQuery(CallbackQuery callback, String text) {
        return answerCallbackQuery(callback.getId(), text, true);
    }

    /**
     * Use this method to send text messages.
     * <p/>
     * For any optional arguments, refer to the Telegram documentation.
     *
     * @param chatId       Unique identifier for the message recipient - {@link User} or {@link co.vandenham.telegram.botapi.types.GroupChat} id
     * @param text         Text of the message to be sent
     * @param optionalArgs Any optional arguments
     * @return the sent {@link Message}
     * @see <a href="https://core.telegram.org/bots/api#sendmessage">https://core.telegram.org/bots/api#sendmessage</a>
     */
    public final ApiResponse<Message> sendMessage(int chatId, String text, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendMessageRequest(chatId, text, optionalArgs));
    }

    /**
     * @see TelegramBot#sendPhoto(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendPhoto(int chatId, File photoFile) {
        return requestExecutor.execute(api, new SendPhotoRequest(chatId, photoFile));
    }

    /**
     * Use this method to send photos.
     *
     * @param chatId       Unique identifier for the message recipient - {@link User} or {@link co.vandenham.telegram.botapi.types.GroupChat} id
     * @param photoFile    Photo to send
     * @param optionalArgs Any optional arguments
     * @return The sent {@link Message}
     * @see <a href="https://core.telegram.org/bots/api#sendphoto">https://core.telegram.org/bots/api#sendphoto</a>
     */
    public final ApiResponse<Message> sendPhoto(int chatId, File photoFile, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendPhotoRequest(chatId, photoFile, optionalArgs));
    }

    /**
     * @see TelegramBot#sendPhoto(int, String, OptionalArgs)
     */
    public final ApiResponse<Message> sendPhoto(int chatId, String photoFileId) {
        return requestExecutor.execute(api, new SendPhotoRequest(chatId, photoFileId));
    }

    /**
     * This version takes a file id instead of a {@link File}
     *
     * @see TelegramBot#sendPhoto(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendPhoto(int chatId, String photoFileId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendPhotoRequest(chatId, photoFileId, optionalArgs));
    }

    /**
     * @see TelegramBot#sendSticker(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendSticker(int chatId, File stickerFile) {
        return requestExecutor.execute(api, new SendStickerRequest(chatId, stickerFile));
    }

    /**
     * Use this method to send .webp stickers.
     * <p/>
     * For any optional arguments, refer to the Telegram documentation.
     *
     * @param chatId       Unique identifier for the message recipient - {@link User} or {@link co.vandenham.telegram.botapi.types.GroupChat} id
     * @param stickerFile  Sticker to send.
     * @param optionalArgs Any optional arguments
     * @return The sent {@link Message}
     * @see <a href="https://core.telegram.org/bots/api#sendsticker">https://core.telegram.org/bots/api#sendsticker</a>
     */
    public final ApiResponse<Message> sendSticker(int chatId, File stickerFile, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendStickerRequest(chatId, stickerFile, optionalArgs));
    }

    /**
     * @see TelegramBot#sendSticker(int, String, OptionalArgs)
     */
    public final ApiResponse<Message> sendSticker(int chatId, String stickerFileId) {
        return requestExecutor.execute(api, new SendStickerRequest(chatId, stickerFileId));
    }

    /**
     * This version takes a file id instead of a {@link File}.
     *
     * @see TelegramBot#sendSticker(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendSticker(int chatId, String stickerFileId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendStickerRequest(chatId, stickerFileId, optionalArgs));
    }

    /**
     * @see TelegramBot#sendVideo(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendVideo(int chatId, File videoFile) {
        return requestExecutor.execute(api, new SendVideoRequest(chatId, videoFile));
    }

    /**
     * Use this method to send video files,
     * Telegram clients support mp4 videos (other formats may be sent as Document ({@link TelegramBot#sendDocument(int, File, OptionalArgs)})).
     * Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
     * <p/>
     * For any optional arguments, refer to the Telegram documentation.
     *
     * @param chatId       Unique identifier for the message recipient - {@link User} or {@link co.vandenham.telegram.botapi.types.GroupChat} id
     * @param videoFile    Video to send
     * @param optionalArgs Any optional arguments.
     * @return The sent {@link Message}
     * @see <a href="https://core.telegram.org/bots/api#sendvideo">https://core.telegram.org/bots/api#sendvideo</a>
     */
    public final ApiResponse<Message> sendVideo(int chatId, File videoFile, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendVideoRequest(chatId, videoFile, optionalArgs));
    }

    /**
     * @see TelegramBot#sendVideo(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendVideo(int chatId, String videoFileId) {
        return requestExecutor.execute(api, new SendVideoRequest(chatId, videoFileId));
    }

    /**
     * This version uses a file id rather than a {@link File}
     *
     * @see TelegramBot#sendVideo(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendVideo(int chatId, String videoFileId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendVideoRequest(chatId, videoFileId, optionalArgs));
    }

    /**
     * @see TelegramBot#sendVoice(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendVoice(int chatId, File voiceFile) {
        return requestExecutor.execute(api, new SendVoiceRequest(chatId, voiceFile));
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message.
     * For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Audio or Document).
     * On success, the sent Message is returned.
     * Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
     *
     * For any optional arguments, refer to the Telegram documentation.
     *
     * @param chatId       Unique identifier for the message recipient — User or GroupChat id
     * @param voiceFile    Audio file to send. You can either pass a file_id as String to resend an audio
     *                     that is already on the Telegram servers, or upload a new audio file using multipart/form-data.
     * @param optionalArgs Any optional arguments
     * @return the sent {@link Message}
     */
    public final ApiResponse<Message> sendVoice(int chatId, File voiceFile, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendVoiceRequest(chatId, voiceFile, optionalArgs));
    }

    /**
     * This version uses a String rather than a File.
     *
     * @see TelegramBot#sendVoice(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendVoice(int chatId, String voiceFileId) {
        return requestExecutor.execute(api, new SendVoiceRequest(chatId, voiceFileId));
    }

    /**
     * This version uses a String rather than a File
     *
     * @see TelegramBot#sendVoice(int, File, OptionalArgs)
     */
    public final ApiResponse<Message> sendVoice(int chatId, String voiceFileId, OptionalArgs optionalArgs) {
        return requestExecutor.execute(api, new SendVoiceRequest(chatId, voiceFileId, optionalArgs));
    }

    /**
     * Convenience method for {@code sendMessage(message.getChat().getId(), text, new OptionalArgs().replyToMessageId(message.getMessageId()))}
     */
    public final ApiResponse<Message> replyTo(Message message, String text) {
        OptionalArgs optionalArgs = new OptionalArgs().replyToMessageId(message.getMessageId());
        return sendMessage(message.getChat().getId(), text, optionalArgs);
    }

    /**
     * This method is called when a new message has arrived.
     * It can safely be overridden by subclasses.
     *
     * @param message The newly arrived {@link Message}
     */
    protected void onMessage(Message message) {
        // Can be overridden by subclasses.
    }

    /**
     * This method is called when a new callback query has arrived.
     * It can safely be overridden by subclasses.
     *
     * @param callback The newly arrived {@link CallbackQuery}
     */
    protected void onCallback(CallbackQuery callback) {
        // Can be overridden by subclasses.
    }

    /**
     * This method is called by this class to process all new {@link Message}s or {@link CallbackQuery}s asynchronously.
     * It <b>must</b> be overridden if {@link TelegramBot#provideExecutorService()} is overridden to return null, otherwise
     * a {@link NullPointerException} may be thrown.
     *
     * @param updates The newly arrived {@link Message}s or {@link CallbackQuery}s
     */
    protected void notifyNewUpdates(List<Updatable> updates) {
        for (final Updatable obj : updates) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    if (obj instanceof Message) {
                        onMessage((Message) obj);
                    } else if (obj instanceof CallbackQuery) {
                        onCallback((CallbackQuery) obj);
                    }
                }
            });
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    handlerNotifier.notifyHandlers(obj);
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
                    logger.error(MarkerFactory.getMarker("SEVERE"), "An exception occurred while polling Telegram.", e);
                    running.set(false);
                }
            }
        }

        public void poll() {
            OptionalArgs optionalArgs = new OptionalArgs().offset(lastUpdateId + 1).timeout(3);
            GetUpdatesRequest request = new GetUpdatesRequest(optionalArgs);

            List<Update> updates = requestExecutor.execute(api, request).getResult();
            if (updates.size() > 0) {
                List<Updatable> newUpdates = processUpdates(updates);
                notifyNewUpdates(newUpdates);
            }
        }

        private List<Updatable> processUpdates(List<Update> updates) {
            List<Updatable> objects = new ArrayList<>();
            for (Update update : updates) {
                if (update.getUpdateId() > lastUpdateId)
                    lastUpdateId = update.getUpdateId();
                if (update.getMessage() != null)
                    objects.add(update.getMessage());
                if (update.getCallbackQuery() != null)
                    objects.add(update.getCallbackQuery());
            }
            return objects;
        }
    }

}
