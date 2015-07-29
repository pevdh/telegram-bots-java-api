package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.types.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HandlerNotifier {

    private final static Logger logger = Logger.getLogger(HandlerNotifier.class.getName());

    private Object objectWithHandlers;
    private Method defaultHandler;
    private List<MessageFilter> messageHandlers = new ArrayList<>();

    public HandlerNotifier(Object objectWithHandlers) {
        this.objectWithHandlers = objectWithHandlers;

        indexHandlers();
    }

    private void indexHandlers() {
        Method[] declaredMethods = objectWithHandlers.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(MessageHandler.class)) {
                logger.info("Found MessageHandler: " + method.getName());
                MessageHandler messageHandlerAnnotation = method.getAnnotation(MessageHandler.class);
                messageHandlers.add(new MessageHandlerFilter(messageHandlerAnnotation.contentTypes(), method));
            } else if (method.isAnnotationPresent(CommandHandler.class)) {
                logger.info("Found CommandHandler: " + method.getName());
                CommandHandler commandHandlerAnnotation = method.getAnnotation(CommandHandler.class);
                messageHandlers.add(new CommandHandlerFilter(commandHandlerAnnotation.value(), method));
            } else if (method.isAnnotationPresent(DefaultHandler.class)) {
                logger.info("Found DefaultHandler: " + method.getName());
                defaultHandler = method;
            }
        }
    }

    public void notifyHandlers(final Message message) {
        Method handler = null;
        for (MessageFilter filter : messageHandlers) {
            if (filter.valid(message)) {
                handler = filter.getHandler();
                break;
            }
        }

        if (handler == null)
            handler = defaultHandler;

        final Method handlerToExecute = handler;
        notifyMessageHandler(handlerToExecute, message);
    }

    private void notifyMessageHandler(Method handler, Message message) {
        try {
            handler.invoke(objectWithHandlers, message);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.log(Level.SEVERE, "An exception occurred while trying to invoke handler '" + handler.getName() + "'", e);
        }
    }

    private interface MessageFilter {
        boolean valid(Message message);
        Method getHandler();
    }

    private static class MessageHandlerFilter implements MessageFilter {

        private Method handler;
        private List<Message.Type> contentTypes;

        public MessageHandlerFilter(Message.Type[] contentTypes, Method handler) {
            this.contentTypes = Arrays.asList(contentTypes);
            this.handler = handler;
        }

        @Override
        public boolean valid(Message message) {
            return contentTypes.contains(message.getType());
        }

        @Override
        public Method getHandler() {
            return handler;
        }
    }

    private static class CommandHandlerFilter implements MessageFilter {

        private Method handler;
        private List<String> commands;

        public CommandHandlerFilter(String[] commands, Method handler) {
            this.handler = handler;
            this.commands = Arrays.asList(commands);
        }

        @Override
        public boolean valid(Message message) {
            return message.getType() == Message.Type.TEXT && commands.contains(extractCommand(message));
        }

        private String extractCommand(Message message) {
            if (isCommand(message)) {
                String text = message.getText();
                return text.split(" ")[0].split("@")[0].substring(1);
            }
            return null;
        }

        private boolean isCommand(Message message) {
            return message.getType() == Message.Type.TEXT && message.getText().startsWith("/");
        }

        @Override
        public Method getHandler() {
            return handler;
        }
    }

}
