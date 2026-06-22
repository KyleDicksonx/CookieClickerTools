package application;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks all things that are called when the program is closed and the JavaFX stop() is called.
 * Methods that are called by @OnShutdown methods will not be marked. 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnShutdown {

}
