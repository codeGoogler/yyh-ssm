package com.enjoy.yuer.server.impl;

import com.enjoy.yuer.annnotaiton.EnjoyService;
import com.enjoy.yuer.server.YuerService;
/**
 * 在我们的Tomcat启动的时候，他会自动的扫描我们自定义的一些注解类
 * 扫描到之后，把Servlet进行实例化（通过反射的方式），那么实例化之后，弹回放到我们的容器里面去
 * 容器就是一个map，put
* 
* @author king
*
*/
@EnjoyService("YuerServletImpl") //在实例化的时候，map.put("yuerServiceImpl",new YuerServletImpl())
public class YuerServletImpl implements YuerService {

	public String query(String name, String age) {
		// TODO Auto-generated method stub
		//按道理来说从这里面查询数据库
		return "name ==="+name+ "  age ==="+age;
	}

}
 