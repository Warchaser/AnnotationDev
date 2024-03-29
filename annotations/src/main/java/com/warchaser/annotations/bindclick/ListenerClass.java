package com.warchaser.annotations.bindclick;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ListenerClass {

    String targetType();

    /**
     * Name of the setter method on the {@linkplain #targetType() target type} fot the listener
     * */
    String setter();

    /**
     * Name of the method on the {@linkplain #targetType() target type} to remove the listener. if
     * empty {@link #setter()} will be used by default
     * */
    String remover() default "";

    /**
     * Fully-qualified class name of the listener type.
     * */
    String type();

    /**
     * Enum which declares the listener callbacks methods. Mutually exclusive to {@link #method()}
     * */
    Class<? extends Enum<?>> callbacks() default NONE.class;

    /**
     * Method data for single-method listener callbacks. Mutually exclusive with {@link #callbacks()}
     * and an error to specify more than one value.
     * */
    ListenerMethod[] method() default {};

    /**
     * Default value for {@link #callbacks()}
     * */
    enum NONE{

    }
}
