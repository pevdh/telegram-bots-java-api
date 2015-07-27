package co.vandenham.telegram.botapi.examples;

import co.vandenham.telegram.botapi.TelegramBot;
import co.vandenham.telegram.botapi.types.Message;

import java.util.logging.Logger;

/**
 * Created by pieter on 25-7-15.
 */
public class SynchronousEchoBot extends TelegramBot {

    private static final Logger log = Logger.getLogger(SynchronousEchoBot.class.getName());

    public SynchronousEchoBot() {
        super("82565878:AAEBPQjeCPZgkCVrEVO7ewx4X7G7FV8JVRw");
    }

    @Override
    protected void onMessage(Message message) {
        if (message.getType() == Message.Type.TEXT) {
            log.info(String.format("%s: %s", message.getChat().getId(), message.getText()));

            sendMessage(message.getChat().getId(), message.getText());
        }
    }

    public static void main(String[] args) {
        TelegramBot bot = new SynchronousEchoBot();
        bot.start();

    }

}
