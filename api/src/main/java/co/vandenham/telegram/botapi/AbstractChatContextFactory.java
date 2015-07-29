package co.vandenham.telegram.botapi;

public interface AbstractChatContextFactory {

    ChatContext createChatContext(int chatId, TelegramBot bot);

}
