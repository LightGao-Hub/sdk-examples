package org.example.sdk.app.common.annotation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ConditionalOnBeanClassCondition.class)
public @interface ConditionalOnBeanClass {
    Class<?>[] value() default {};
    Class<?>[] name();
    String havingValue() default "default";
    boolean matchIfMissing() default false;
}

