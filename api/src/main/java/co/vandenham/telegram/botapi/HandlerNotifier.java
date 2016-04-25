package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.types.CallbackQuery;
import co.vandenham.telegram.botapi.types.Message;
import co.vandenham.telegram.botapi.types.Updatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandlerNotifier {

    private final static Logger logger = LoggerFactory.getLogger(HandlerNotifier.class.getName());

    private Object objectWithHandlers;
    private Method defaultHandler;
    private List<UpdatableFilter> updateHandlers = new ArrayList<>();

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
                updateHandlers.add(new MessageHandlerFilter(messageHandlerAnnotation.contentTypes(), method));
            } else if (method.isAnnotationPresent(CallbackHandler.class)) {
                logger.info("Found CallbackHandler: " + method.getName());
                CallbackHandler callbackHandlerAnnotation = method.getAnnotation(CallbackHandler.class);
                updateHandlers.add(new CallbackHandlerFilter(callbackHandlerAnnotation.contentTypes(), method));
            } else if (method.isAnnotationPresent(CommandHandler.class)) {
                logger.info("Found CommandHandler: " + method.getName());
                CommandHandler commandHandlerAnnotation = method.getAnnotation(CommandHandler.class);
                updateHandlers.add(new CommandHandlerFilter(commandHandlerAnnotation.value(), method));
            } else if (method.isAnnotationPresent(DefaultHandler.class)) {
                logger.info("Found DefaultHandler: " + method.getName());
                defaultHandler = method;
            }
        }
    }

    public void notifyHandlers(final Updatable update) {
        Method handler = null;
        for (UpdatableFilter filter : updateHandlers) {
            if (filter.valid(update)) {
                handler = filter.getHandler();
                break;
            }
        }

        if (handler == null)
            handler = defaultHandler;

        final Method handlerToExecute = handler;
        notifyMessageHandler(handlerToExecute, update);
    }

    private void notifyMessageHandler(Method handler, Updatable message) {
        try {
            handler.invoke(objectWithHandlers, message);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(MarkerFactory.getMarker("SEVERE"), "An exception occurred while trying to invoke handler '" + handler.getName() + "'", e);
        }
    }

    private interface UpdatableFilter {
        boolean valid(Updatable obj);
        Method getHandler();
    }

    private static class MessageHandlerFilter implements UpdatableFilter {

        private Method handler;
        private List<Message.Type> contentTypes;

        public MessageHandlerFilter(Message.Type[] contentTypes, Method handler) {
            this.contentTypes = Arrays.asList(contentTypes);
            this.handler = handler;
        }

        @Override
        public boolean valid(Updatable obj) {
            if (obj instanceof Message) {
                return contentTypes.contains(((Message)obj).getType());
            } else {
                return false;
            }
        }

        @Override
        public Method getHandler() {
            return handler;
        }
    }

    private static class CallbackHandlerFilter implements UpdatableFilter {

        private Method handler;
        private List<CallbackQuery.Type> contentTypes;

        public CallbackHandlerFilter(CallbackQuery.Type[] contentTypes, Method handler) {
            this.contentTypes = Arrays.asList(contentTypes);
            this.handler = handler;
        }

        @Override
        public boolean valid(Updatable obj) {
            if (obj instanceof CallbackQuery) {
                return contentTypes.contains(((CallbackQuery)obj).getType());
            } else {
                return false;
            }
        }

        @Override
        public Method getHandler() {
            return handler;
        }
    }

    private static class CommandHandlerFilter implements UpdatableFilter {

        private Method handler;
        private List<String> commands;

        public CommandHandlerFilter(String[] commands, Method handler) {
            this.handler = handler;
            this.commands = Arrays.asList(commands);
        }

        @Override
        public boolean valid(Updatable obj) {
            if (obj instanceof Message) {
                Message message = (Message)obj;
                return message.getType() == Message.Type.TEXT && commands.contains(extractCommand(message));
            } else {
                return false;
            }
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
