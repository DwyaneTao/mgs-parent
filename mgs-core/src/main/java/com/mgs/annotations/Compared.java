package com.mgs.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于比较的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Compared {

    //变量名
    String variableName();

    //分割符
    String split() default  "";

    //变量类型名称
    String enumsName() default "";

}

