package com.enjoy.yuer.annnotaiton;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作用范围一般在成员变量上使用
 * @author king
 *
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented//Java doc
public  @interface EnjoyAutowired {
	String value() default "";
}
