package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.types.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatContextTelegramBot extends TelegramBot {

    private final Map<Integer, ChatContext> contextMap = new HashMap<>();
    private AbstractChatContextFactory chatContextFactory;

    public ChatContextTelegramBot(String botToken, AbstractChatContextFactory chatContextFactory) {
        this(botToken, chatContextFactory, true);
    }

    public ChatContextTelegramBot(String botToken, AbstractChatContextFactory chatContextFactory, boolean sendAsync) {
        super(botToken);
        this.chatContextFactory = chatContextFactory;
    }

    public void add(ChatContext context) {
        contextMap.put(context.getChatId(), context);
    }

    public void addAll(List<? extends ChatContext> contextList) {
        for (ChatContext chatContext : contextList)
            add(chatContext);
    }

    @Override
    protected void onStart() {
        for (ChatContext chatContext : contextMap.values())
            chatContext.onStart();
    }

    @Override
    protected void onStop() {
        for (ChatContext chatContext : contextMap.values())
            chatContext.onStop();
    }

    @Override
    protected void onMessage(Message message) {
        int chatId = message.getChat().getId();

        ChatContext chatContext;
        synchronized (contextMap) {
            chatContext = contextMap.get(chatId);
            if (chatContext == null)
                chatContext = createNewChatContext(chatId);
        }

        chatContext.passMessage(message);
    }

    private ChatContext createNewChatContext(int chatId) {
        ChatContext chatContext = chatContextFactory.createChatContext(chatId, this);
        contextMap.put(chatId, chatContext);
        return chatContext;
    }
}
