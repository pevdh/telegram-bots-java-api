package co.vandenham.telegram.botapi;

import co.vandenham.telegram.botapi.types.CallbackQuery;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CallbackHandler {
    CallbackQuery.Type[] contentTypes() default CallbackQuery.Type.MESSAGE;
}
