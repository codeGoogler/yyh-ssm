package com.enjoy.yuer.server.impl;

import com.enjoy.yuer.annnotaiton.EnjoyService;
import com.enjoy.yuer.server.YuerService;
/**
 * �����ǵ�Tomcat������ʱ�������Զ���ɨ�������Զ����һЩע����
 * ɨ�赽֮�󣬰�Servlet����ʵ������ͨ������ķ�ʽ������ôʵ����֮�󣬵��طŵ����ǵ���������ȥ
 * ��������һ��map��put
* 
* @author king
*
*/
@EnjoyService("YuerServletImpl") //��ʵ������ʱ��map.put("yuerServiceImpl",new YuerServletImpl())
public class YuerServletImpl implements YuerService {

	public String query(String name, String age) {
		// TODO Auto-generated method stub
		//��������˵���������ѯ���ݿ�
		return "name ==="+name+ "  age ==="+age;
	}

}
 