package com.enjoy.yuer.annnotaiton;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ���÷�Χһ���ڷ���������(����)ʹ��
 * @author king
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})//��ʾ��java����,�������ڷ����Ͽ���ʹ��
@Retention(RetentionPolicy.RUNTIME)//�����е�ʱ��
@Documented //����һ��Java doC
//@Inherited //��ʶע����Ա��̳�
public @interface  EnjoyRequestMapping {
	//ע�������Ա���֮ eg:@EnjoyController("/123")
	String value() default "";
}
