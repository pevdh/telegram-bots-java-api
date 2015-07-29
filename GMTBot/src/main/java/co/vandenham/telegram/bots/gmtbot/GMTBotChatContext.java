package co.vandenham.telegram.bots.gmtbot;

import co.vandenham.telegram.botapi.*;
import co.vandenham.telegram.botapi.types.Message;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class GMTBotChatContext extends ChatContext {

    private final static Logger logger = Logger.getLogger(GMTBotChatContext.class.getName());
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a (EEEE)");

    private final Pattern timeZoneRegex = Pattern.compile("(GMT)([+-])([0-9]{1,2})");

    private final List<TimeZone> timeZones = new ArrayList<>();

    public GMTBotChatContext(int chatId, TelegramBot bot) {
        super(chatId, bot);
    }

    public static void main(String[] args) {
        TelegramBot bot = new ChatContextTelegramBot(System.getenv("TOKEN"), new AbstractChatContextFactory() {
            @Override
            public ChatContext createChatContext(int chatId, TelegramBot bot) {
                return new GMTBotChatContext(chatId, bot);
            }
        });

        bot.start();
    }

    @CommandHandler("times")
    public void handleTimeCommand(Message message) {
        String text = message.getText();

        String[] arguments;
        if ((arguments = getArguments(text)).length > 0) {
            String timeZoneText = arguments[0];
            if (TimeZones.isValidTimeZone(timeZoneText))
                sendCurrentTimeOf(TimeZone.getTimeZone(timeZoneText));
            else
                replyTo(message, "Invalid timezone. Please specify timezones this way: GMT(+/-)(0 - 12)\nExample: '/time gmt-11'");
        } else {
            sendCurrentTimes();
        }
    }

    private void sendCurrentTimeOf(TimeZone timeZone) {
        StringBuilder builder = new StringBuilder();
        builder.append(TimeZones.getCurrentTime(timeZone));
        sendMessage(builder.toString());
    }

    private void sendCurrentTimes() {
        StringBuilder builder = new StringBuilder();
        if (timeZones.size() > 0) {
            builder.append("These are the current times in the world:\n\n");

            for (TimeZone timeZone : timeZones) {
                builder.append(TimeZones.getCurrentTime(timeZone));
                builder.append("\n");
            }
        } else {
            builder.append("There are currently no configured timezones.\nUse '/add' to add one.");
        }

        sendMessage(builder.toString());
    }

    private String[] getArguments(String text) {
        String[] split = text.split(" ");
        return Arrays.copyOfRange(split, 1, split.length);
    }

    @CommandHandler("add")
    public void handleAddCommand(Message message) {
        String text = message.getText();

        String[] arguments;
        if ((arguments = getArguments(text)).length == 0) {
            replyTo(message, "Please specify a timezone.\nSend '/help' to find out how.");
            return;
        }

        String timeZoneText = arguments[0].toUpperCase();

        if (TimeZones.isValidTimeZone(timeZoneText)) {
            TimeZone timeZone = TimeZone.getTimeZone(timeZoneText);
            timeZones.add(timeZone);
            Collections.sort(timeZones, TimeZones.TimeZoneComparator.INSTANCE);

            replyTo(message, "Successfully added " + timeZoneText).getResult();
            sendConfiguredTimeZones();
        } else {
            replyTo(message, "Invalid timezone. Please specify timezones this way: GMT(+/-)(0 - 12)\nExample: '/add gmt-11'");
        }
    }

    private void sendConfiguredTimeZones() {
        StringBuilder builder = new StringBuilder();

        if (timeZones.size() > 0) {
            builder.append("Currently added timezones:\n\n");
            for (TimeZone timeZone : timeZones){
                builder.append(timeZone.getID());
                builder.append('\n');
            }
        } else {
            builder.append("There are currently no configured timezones.\nUse '/add' to add one.");
        }
        sendMessage(builder.toString());
    }

    @CommandHandler("remove")
    public void handleRemove(Message message) {
        String text = message.getText();

        String[] arguments;
        if ((arguments = getArguments(text)).length == 0) {
            replyTo(message, "Please specify a timezone.\nSend '/help' to find out how.");
            return;
        }

        String timeZoneText = arguments[0].toUpperCase();

        if (TimeZones.isValidTimeZone(timeZoneText)) {
            TimeZone timeZone = TimeZone.getTimeZone(timeZoneText);
            if (timeZones.contains(timeZone)) {
                timeZones.remove(timeZone);
                replyTo(message, "Successfully removed " + timeZone.getID()).getResult();
            } else {
                replyTo(message, timeZoneText + " is currently not in the default timezone list.").getResult();
            }
            sendConfiguredTimeZones();
        } else {
            replyTo(message, "Invalid timezone. Please specify timezones this way: GMT(+/-)(0 - 12).\nExample: '/remove gmt-11'");
        }
    }

    @Override
    protected void onMessage(Message message) {
        if (message.getType() == Message.Type.TEXT)
            logger.info(message.getFrom().getFirstName() + ": " + message.getText());
    }

}
