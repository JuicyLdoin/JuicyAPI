package net.juicy.api.utils.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandArgument {

    String[] aliases() default {};

    String[] permissions() default {};

    boolean onlyPlayers() default false;

    boolean allArgs() default false;

}