package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.types.Message;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistrableTelegramBot extends TelegramBot {

    private boolean async;
    private List<MessageListener> messageListeners = new CopyOnWriteArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public RegistrableTelegramBot(String botToken) {
        this(botToken, false);
    }

    public RegistrableTelegramBot(String botToken, boolean async) {
        super(botToken, async);
    }

    public void register(MessageListener messageListener) {
        messageListeners.add(messageListener);
    }

    public void unregister(MessageListener messageListener) {
        messageListeners.remove(messageListener);
    }

    @Override
    protected ExecutorService provideExecutorService() {
        return null;
    }


    @Override
    protected void notifyNewMessages(List<Message> messages) {
        for (final Message message : messages) {
            for (final MessageListener messageListener : messageListeners) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        messageListener.onMessage(RegistrableTelegramBot.this, message);
                    }
                });
            }
        }
    }

    public interface MessageListener {

        void onMessage(TelegramBot bot, Message message);

    }
}
