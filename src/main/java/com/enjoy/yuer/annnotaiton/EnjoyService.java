package com.enjoy.yuer.annnotaiton;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ���÷�Χһ��������ʹ��
 * @author king
 *
 */
@Target({ElementType.TYPE})//��ʾ��java����,�����Ͽ���ʹ��
@Retention(RetentionPolicy.RUNTIME)//�����е�ʱ��
@Documented //����һ��Java doC
//@Inherited //��ʶע����Ա��̳�
public @interface  EnjoyService {
	//ע�������Ա���֮ eg:@EnjoyController("/123")
	String value() default "";
}
