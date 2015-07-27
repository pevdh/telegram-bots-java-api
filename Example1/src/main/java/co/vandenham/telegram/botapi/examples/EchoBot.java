package co.vandenham.telegram.botapi.examples;

import co.vandenham.telegram.botapi.TelegramBot;
import co.vandenham.telegram.botapi.types.Message;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by pieter on 25-7-15.
 */
public class EchoBot extends TelegramBot {

    private static final Logger log = Logger.getLogger(EchoBot.class.getName());

    public EchoBot() {
        super("TOKEN");
    }

    @Override
    protected void onMessages(List<Message> messages) {
        for (Message message : messages)
            if (message.getType() == Message.Type.TEXT)
                sendMessage(message.getChat().getId(), message.getText());
    }


    public static void main(String[] args) {
        log.info("Starting.");

        TelegramBot bot = new EchoBot();
        bot.start();

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
