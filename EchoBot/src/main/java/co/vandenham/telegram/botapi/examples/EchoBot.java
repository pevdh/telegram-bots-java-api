package co.vandenham.telegram.botapi.examples;

import co.vandenham.telegram.botapi.CommandHandler;
import co.vandenham.telegram.botapi.DefaultHandler;
import co.vandenham.telegram.botapi.MessageHandler;
import co.vandenham.telegram.botapi.TelegramBot;
import co.vandenham.telegram.botapi.types.Message;

import java.util.logging.Logger;

public class EchoBot extends TelegramBot {

    private static final Logger log = Logger.getLogger(EchoBot.class.getName());

    public EchoBot(boolean async) {
        super(System.getenv("TOKEN"), async);
    }

    public static void main(String[] args) {
        TelegramBot bot = new EchoBot(true);
        bot.start();
    }

    @CommandHandler({"start", "help"})
    public void handleHelp(Message message) {
        replyTo(message, "Hi there! I am here to echo all your kind words back to you!");
    }

    @MessageHandler(contentTypes = Message.Type.TEXT)
    public void handleTextMessage(Message message) {
        log.info(String.format("%s: %s", message.getChat().getId(), message.getText()));
        replyTo(message, message.getText());
    }

    @DefaultHandler
    public void handleDefault(Message message) {
        replyTo(message, "Say what?");
    }
}
