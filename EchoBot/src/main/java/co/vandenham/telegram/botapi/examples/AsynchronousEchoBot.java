package co.vandenham.telegram.botapi.examples;

import co.vandenham.telegram.botapi.TelegramBot;
import co.vandenham.telegram.botapi.types.Message;

import java.util.logging.Logger;

public class AsynchronousEchoBot extends TelegramBot {

    private static final Logger log = Logger.getLogger(SynchronousEchoBot.class.getName());


    public AsynchronousEchoBot() {
        super(System.getenv("TOKEN"));
    }

    @CommandHandler({"start", "help"})
    public void handleHelp(Message message) {
        replyTo(message, "Hi there! I am here to echo all your kind words back to you!");
    }

    @DefaultHandler
    public void handleDefault(Message message) {
        if (message.getType() == Message.Type.TEXT) {
            log.info(String.format("%s: %s", message.getChat().getId(), message.getText()));

            sendMessage(message.getChat().getId(), message.getText());
        }
    }

    public static void main(String[] args) {
        TelegramBot bot = new AsynchronousEchoBot();
        bot.start();
    }
}
