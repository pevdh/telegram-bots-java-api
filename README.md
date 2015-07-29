# TelegramBots4Java

##### TelegramBots4Java is a Java implementation of the [Telegram Bot API](https://core.telegram.org/bots/api)

This API is designed to be *extensible*, while preserving *simplicity* and *conciseness*.
It provides [core classes](https://cdn.rawgit.com/pevdh/telegram-bots-java-api/master/docs/co/vandenham/telegram/botapi/requests/package-summary.html) to interact with the Telegram Bot API, and it encapsulates these classes in [one simple, extensible class](https://cdn.rawgit.com/pevdh/telegram-bots-java-api/master/docs/co/vandenham/telegram/botapi/TelegramBot.html). 
However, nothing holds you back to use the [core classes](https://cdn.rawgit.com/pevdh/telegram-bots-java-api/master/docs/co/vandenham/telegram/botapi/requests/package-summary.html) directly.

## How to obtain a Jar

### Unix
```
$ git clone https://github.com/pevdh/telegram-bots-java-api.git
$ cd TelegramBots4Java/
$ ./gradlew jar
```
You'll find the .jar file in the api/build/libs directory.

### Windows
```
$ git clone https://github.com/pevdh/telegram-bots-java-api.git
$ cd TelegramBots4Java/
$ gradlew.bat jar
```
You'll find the .jar file in the api\build\libs directory.

## How to use

### A simple echo bot
*The import statements are left out for simplicity.*

```java
public class EchoBot extends TelegramBot {

    public EchoBot() {
        super("<YOUR_BOT_TOKEN>");
    }

    // This handler gets called whenever a user sends /start or /help
    @CommandHandler({"start", "help"})
    public void handleHelp(Message message) {
        replyTo(message, "Hi there! I am here to echo all your kind words back to you!");
    }

    // This handler gets called whenever a user sends a general text message.
    @MessageHandler(contentTypes = Message.Type.TEXT)
    public void handleTextMessage(Message message) {
        System.out.println(String.format("%s: %s", message.getChat().getId(), message.getText()));
        replyTo(message, message.getText());
    }

    // This is the default handler, called when the other two handlers don't apply.
    @DefaultHandler
    public void handleDefault(Message message) {
        replyTo(message, "Say what?");
    }

    public static void main(String[] args) {
        TelegramBot bot = new EchoBot();
        bot.start();
    }
}
```
### Further reference
This project has [Javadocs](https://cdn.rawgit.com/pevdh/telegram-bots-java-api/master/docs/index.html).

*TelegramBot.java* provides a good entry point into this API. You can have a look at the [Javadoc reference](https://cdn.rawgit.com/pevdh/telegram-bots-java-api/master/docs/co/vandenham/telegram/botapi/TelegramBot.html) for this class, or look at the [source code](https://github.com/pevdh/TelegramBots4Java/blob/master/api/src/main/java/co/vandenham/telegram/botapi/TelegramBot.java) directly.
