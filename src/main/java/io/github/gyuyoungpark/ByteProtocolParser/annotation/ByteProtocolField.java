package io.github.gyuyoungpark.ByteProtocolParser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ByteProtocolField {
    int start() default 0;
    int length() default 0;
    String desc() default "";
}
