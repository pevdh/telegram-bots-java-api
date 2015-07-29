package co.vandenham.telegram.bots.gmtbot;

import co.vandenham.telegram.botapi.*;
import co.vandenham.telegram.botapi.types.Message;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GMTBotChatContext extends ChatContext {

    private final static Logger logger = Logger.getLogger(GMTBotChatContext.class.getName());

    private List<TimeZone> timeZones = new ArrayList<>();

    public GMTBotChatContext(int chatId, TelegramBot bot) {
        super(chatId, bot);
    }

    public static void main(String[] args) {
        ChatContextTelegramBot bot = new ChatContextTelegramBot(System.getenv("GMTBOT_TOKEN"), new AbstractChatContextFactory() {
            @Override
            public ChatContext createChatContext(int chatId, TelegramBot bot) {
                return new GMTBotChatContext(chatId, bot);
            }
        });

        bot.addAll(restoreContexts(bot));

        bot.start();
    }

    private static synchronized void saveContext(GMTBotChatContext chatContext) {
        try {
            File saveFile = getFileFor(chatContext);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(saveFile.getPath()));
            objectOutputStream.writeObject(chatContext.timeZones);
            logger.info("Saved to file: " + saveFile.getPath());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not create savefile.", e);
        }
    }

    private static List<GMTBotChatContext> restoreContexts(ChatContextTelegramBot bot) {
        List<GMTBotChatContext> chatContexts = new ArrayList<>();
        try {
            File resourceDir = getResourceDirectory();

            File[] listFiles = resourceDir.listFiles();
            if (listFiles == null)
                return chatContexts;

            for (File saveFile : listFiles) {
                if (!saveFile.getPath().endsWith(".data"))
                    continue;

                chatContexts.add(restoreContext(bot, saveFile));
            }
        } catch (IOException|ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Could not restore ChatContexts.", e);
        }
        return chatContexts;
    }

    private static GMTBotChatContext restoreContext(ChatContextTelegramBot bot, File file) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file.getPath()));
        @SuppressWarnings("unchecked") List<TimeZone> timeZones = (List<TimeZone>) objectInputStream.readObject();
        String fileName = file.getName();

        int chatId = Integer.valueOf(fileName.split("\\.")[0]);

        GMTBotChatContext chatContext = new GMTBotChatContext(chatId, bot);
        chatContext.timeZones = timeZones;

        return chatContext;
    }

    private static synchronized File getFileFor(GMTBotChatContext chatContext) throws IOException {
        File resourceDir = getResourceDirectory();
        File saveFile = new File(resourceDir.getPath() + "/" + chatContext.getChatId() + ".data");
        saveFile.createNewFile();
        return saveFile;
    }

    private static synchronized File getResourceDirectory() throws IOException {
        URL url = GMTBotChatContext.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        File jarDir = new File(jarPath).getParentFile();
        File resourceDir = new File(jarDir.getPath() + "/resources");
        if (!resourceDir.exists()) {
            logger.info("Created new directory: " + resourceDir.getPath());
            resourceDir.mkdir();
        }
        return resourceDir;
    }

    @CommandHandler({"start", "help"})
    public void handleHelp(Message message) {
        StringBuilder builder = new StringBuilder();
        builder.append("Hi there! My name is GMTBot. ");
        builder.append("My job is to keep you up to date with the different timezones! ");
        builder.append("The currently available commands are:\n\n");
        builder.append("  -  /times <optional timezone>  -- Shows you the current times for the configured timezones,");
        builder.append(" or just for the timezone you passed as <optional timezone>. Example: '/times', '/times gmt-7'.\n\n");
        builder.append("  -  /add <GMT(+/-)(0 - 12)> -- Adds a timezone to the list shown when you send '/times'. Example: '/add gmt+4'.\n\n");
        builder.append("  -  /remove <GMT(+/-)(0 - 12)> -- Removes a timezone from the list.\n\n");
        builder.append("  -  /clear -- Clears the whole list at once.\n\n");
        builder.append("  -  /help -- Shows this message\n\n");
        builder.append("I hope you find me useful!");
        replyTo(message, builder.toString());
    }

    @CommandHandler("times")
    public void handleTimeCommand(Message message) {
        String text = message.getText();

        String[] arguments;
        if ((arguments = getArguments(text)).length > 0) {
            String timeZoneText = arguments[0].toUpperCase();
            if (TimeZones.isValidTimeZone(timeZoneText))
                sendCurrentTimeOf(TimeZone.getTimeZone(timeZoneText));
            else
                replyTo(message, "Invalid timezone. Please specify timezones this way: GMT(+/-)(0 - 12)\nExample: '/time gmt-11'");
        } else {
            sendCurrentTimes();
        }
    }

    private void sendCurrentTimeOf(TimeZone timeZone) {
        sendMessage(TimeZones.getCurrentTime(timeZone));
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
            if (timeZones.contains(timeZone)) {
                replyTo(message, "This timezone is already in the default timezone list.");
                return;
            }

            timeZones.add(timeZone);
            Collections.sort(timeZones, TimeZones.TimeZoneComparator.INSTANCE);

            replyTo(message, "Successfully added " + timeZoneText).getResult();
            sendConfiguredTimeZones();
            saveContext(this);
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
            saveContext(this);
        } else {
            replyTo(message, "Invalid timezone. Please specify timezones this way: GMT(+/-)(0 - 12).\nExample: '/remove gmt-11'");
        }
    }

    @CommandHandler("clear")
    public void handleClear(Message message) {
        timeZones.clear();
        replyTo(message, "Alright, no timezones set anymore.");
        saveContext(this);
    }

    @Override
    protected void onMessage(Message message) {
        if (message.getType() == Message.Type.TEXT)
            logger.info(message.getFrom().getFirstName() + ": " + message.getText());
    }

}
