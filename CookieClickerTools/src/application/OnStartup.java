package application;

import java.lang.annotation.*;

/**
 * This annotation marks all things that are called directly on startup.
 * Methods that are called by @OnStartup methods will not be marked. 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnStartup {
	//String message() default "no message";
	
	
	/**
	 * You can use
	 * .isAnnotationPresent(AnnotationName.class)
	 * to check if an annotation is on something
	 * 
	 * method.invoke(object to be invoked on)
	 */
 	
	
}
