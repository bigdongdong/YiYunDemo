package com.chen.firstdemo.recyclers.better_adapters.activity.adapters;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.FIELD})
public @interface Item {
    Class<?> value();
//    String tag() default "";
}
