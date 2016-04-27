package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.types.CallbackQuery;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.Updatable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistrableTelegramBot extends TelegramBot {

    private boolean async;
    private List<UpdateListener> updateListeners = new CopyOnWriteArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public RegistrableTelegramBot(String botToken) {
        this(botToken, false);
    }

    public RegistrableTelegramBot(String botToken, boolean async) {
        super(botToken, async);
    }

    public void register(UpdateListener messageListener) {
        updateListeners.add(messageListener);
    }

    public void unregister(UpdateListener messageListener) {
        updateListeners.remove(messageListener);
    }

    @Override
    protected ExecutorService provideExecutorService() {
        return null;
    }

    @Override
    protected void notifyNewUpdates(List<Updatable> updates) {
        for (final Updatable obj : updates) {
            for (final UpdateListener updateListener : updateListeners) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        if (obj instanceof Message) {
                            updateListener.onMessage(RegistrableTelegramBot.this, (Message)obj);
                        } else if (obj instanceof CallbackQuery) {
                            updateListener.onCallback(RegistrableTelegramBot.this, (CallbackQuery)obj);
                        }
                    }
                });
            }
        }
    }

    public interface UpdateListener {

        void onMessage(TelegramBot bot, Message message);
        void onCallback(TelegramBot bot, CallbackQuery callback);

    }

}
