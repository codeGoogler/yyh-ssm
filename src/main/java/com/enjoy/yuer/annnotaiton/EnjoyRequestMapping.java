package com.enjoy.yuer.annnotaiton;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 作用范围一般在方法的体内(参数)使用
 * @author king
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})//表示在java类上,或者是在方法上可以使用
@Retention(RetentionPolicy.RUNTIME)//在运行的时候
@Documented //包含一个Java doC
//@Inherited //辨识注解可以被继承
public @interface  EnjoyRequestMapping {
	//注解后面可以被跟之 eg:@EnjoyController("/123")
	String value() default "";
}
