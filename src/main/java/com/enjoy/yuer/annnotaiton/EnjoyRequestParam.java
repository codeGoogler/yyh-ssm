package com.enjoy.yuer.annnotaiton;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ���÷�Χһ�������ϣ�������ʹ��
 * @author king
 *
 */
@Target({ElementType.PARAMETER})//��ʾ��java�ķ�����Ĳ�����ʹ�ÿ���ʹ��
@Retention(RetentionPolicy.RUNTIME)//�����е�ʱ��
@Documented //����һ��Java doC
//@Inherited //��ʶע����Ա��̳�
public @interface  EnjoyRequestParam {
	//ע�������Ա���֮ eg:@EnjoyController("/123")
	String value() default "";
}
