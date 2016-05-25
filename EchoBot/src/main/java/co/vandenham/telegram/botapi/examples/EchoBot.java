package co.vandenham.telegram.botapi.examples;

import ch.qos.logback.classic.Level;
import co.vandenham.telegram.botapi.CommandHandler;
import co.vandenham.telegram.botapi.DefaultHandler;
import co.vandenham.telegram.botapi.MessageHandler;
import co.vandenham.telegram.botapi.TelegramBot;
import co.vandenham.telegram.botapi.requests.OptionalArgs;
import co.vandenham.telegram.botapi.requests.TelegramApi;
import co.vandenham.telegram.botapi.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoBot extends TelegramBot {

    private static final Logger log = LoggerFactory.getLogger(EchoBot.class);

    public EchoBot(boolean async) {
        super(System.getenv("TOKEN"), async);
    }

    public static void main(String[] args) {
        ch.qos.logback.classic.Logger loggerRoot = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        ch.qos.logback.classic.Logger loggerObj = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(TelegramApi.class);
        loggerObj.setLevel(Level.TRACE);

        TelegramBot bot = new EchoBot(true);
        bot.start();
    }

    @CommandHandler({"start", "help"})
    public void handleHelp(Message message) {
        replyTo(message, "Hi there! I am here to echo all your kind words back to you!");
    }

    @CommandHandler({"inline"})
    public void handleInlineTest(Message message) {
        InlineKeyboardMarkup responseKeyboard = new InlineKeyboardMarkup.Builder().
                row(new CallbackKeyboardButton("It's cool", "COOL:123456")).
                row(new CallbackKeyboardButton("OMG", "OMG:123456")).
                row(new CallbackKeyboardButton("WTF!", "WTF:123456")).
                row(new UrlKeyboardButton("Tell me more", "http://google.co.uk")).build();

        sendMessage(message.getChat().getId(), "Take this message and respond to it if you dare", new OptionalArgs().replyMarkup(responseKeyboard));

    }

    @MessageHandler(contentTypes = Message.Type.TEXT)
    public void handleTextMessage(Message message) {
        log.info(String.format("%s: %s", message.getChat().getId(), message.getText()));
        replyTo(message, message.getText());
    }

    @DefaultHandler
    public void handleDefault(Updatable message) {
        if (message instanceof Message) {
            replyTo((Message)message, "Say what?");
        } else {
            answerCallbackQuery((CallbackQuery)message, "Thanks! Got it!");
        }
    }
}
