package com.enjoy.yuer.servlet;


import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enjoy.yuer.annnotaiton.EnjoyAutowired;
import com.enjoy.yuer.annnotaiton.EnjoyController;
import com.enjoy.yuer.annnotaiton.EnjoyRequestMapping;
import com.enjoy.yuer.annnotaiton.EnjoyRequestParam;
import com.enjoy.yuer.annnotaiton.EnjoyService;
import com.enjoy.yuer.contorller.YuerController;
//import com.enjoy.yuer.server.YuerService;

public class DispatcherServlet extends HttpServlet{

	List<String> classNameLsList = new ArrayList<String>();
	Map<String, Object> beansMap = new HashMap<String, Object>();
	Map<String, Object> handleMap = new HashMap<String, Object>();
	//注意：init是在Tomcat启动的时候进行加载的，（实例化 ，bean）
	//而doGet和doPost是在业务需要运行的时候才去执行
	public void init(ServletConfig servletConfig)  {
		System.out.println("====================I am come back!=======================");
		//第一步：进行扫描
		doScannerClass("com.enjoy"); //扫描所有的class
		//根据扫描的list，生成对象（反射对相关）
		doInstance();
		//注入
		doAntowored();
		//建立地址和方法之前的对应关系
		urpMapping();
	}

	private void urpMapping() {
		for(Map.Entry<String, Object> enrtyEntry :beansMap.entrySet()) {
			Object instance = enrtyEntry.getValue();
			Class<?> clazz =  instance.getClass();
			if(clazz.isAnnotationPresent(EnjoyController.class)) {
				EnjoyRequestMapping mapping = clazz.getAnnotation(EnjoyRequestMapping.class);
				String classPath = mapping.value(); // "/yuer"
				Method [] methods =  clazz.getMethods();//获取类中 的方法
				for (Method method : methods) {
					if(method.isAnnotationPresent(EnjoyRequestMapping.class)){
						EnjoyRequestMapping mapping1 = method.getAnnotation(EnjoyRequestMapping.class);
						String methonPath =  mapping1.value();
						//路径的组合，对象的方法
						handleMap.put(classPath+methonPath, method);
					}else {
						continue;
					}
				}
			}
		}
		/*System.out.println( "===============value：      ");
		for(Map.Entry<String, Object> enrtyEntry :handleMap.entrySet()) {
			
			Object instance = enrtyEntry.getValue();
			Class<?> clazz =  instance.getClass();
			System.out.println("key: " +enrtyEntry.getKey() +"===============value：      "+instance);
		}*/
	}

	private void doAntowored() {
		//遍历bean 
		for(Map.Entry<String, Object> enrtyEntry :beansMap.entrySet()) {
			Object instance = enrtyEntry.getValue();
			Class<?> clazz =  instance.getClass();
			if(clazz.isAnnotationPresent(EnjoyController.class)) {
				Field []  fields =clazz.getDeclaredFields();//拿到类中的所有的属性
				for (Field field :fields) {
					if(field.isAnnotationPresent(EnjoyAutowired.class)) {
						EnjoyAutowired auto = field.getAnnotation(EnjoyAutowired.class);
						String key = auto.value();//拿到setvice的key
						System.out.println("key====>"+key);
						System.out.println("value====>"+beansMap.get(key));
						field.setAccessible(true);
						try {
							field.set(instance, beansMap.get(key));

						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}else {
						continue;
					}
				}
			}else {
				continue;
			}

		}

	}

	private void doInstance() {
		
		int  i  = 0;
		for (String subPath : classNameLsList) {
			System.out.println("subPath: "+i+" "+subPath);
			i++;
		}
		
		// TODO Auto-generated method stub
		for(String classNameString :classNameLsList ) {
			String cnString = classNameString.replace(".class", "");//com.enjoy.yuer.Controller 
			Class<?> clazz;
			try {
				clazz = Class.forName(cnString);
				if(clazz.isAnnotationPresent(EnjoyController.class)) {
					//创建一个对象，
					Object inscanceObject;
					try {
						inscanceObject = clazz.newInstance();
						EnjoyRequestMapping mapping =clazz.getAnnotation(EnjoyRequestMapping.class);
						String key = mapping.value();//拿到key，
						beansMap.put(key,inscanceObject);//存放到IOC容器里面去 
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(clazz.isAnnotationPresent(EnjoyService.class)) {
					Object inscanceObject;
					try {
						inscanceObject = clazz.newInstance();
						EnjoyService service =clazz.getAnnotation(EnjoyService.class);
						String key = service.value();
						beansMap.put(key,inscanceObject);
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else {
					continue;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		System.out.println( "\n============beansMap==============");
		for(Map.Entry<String, Object> enrtyEntry :beansMap.entrySet()) {
			
			Object instance = enrtyEntry.getValue();
			Class<?> clazz =  instance.getClass();
			System.out.println("key: " +enrtyEntry.getKey() +"=======beansMap========    value：      "+instance+"     "+clazz);
		}
	}

	private void doScannerClass(String basePackage) {
		// TODO Auto-generated method stub
		//扫描所有的类路径，eg:E:/WORKSPACE/project/com/enjoy/yuer.Controller.class
		String urlPath = "/"+basePackage.replaceAll("\\.", "/");
		System.out.println("打印路径--->urlPath: "+urlPath);
		URL url = this.getClass().getClassLoader().getResource(urlPath);
		System.out.println("打印URL："+url);
		String fileUrlString =  url.getFile();
		File file =  new File(fileUrlString);
		String [] filesStrings = file.list();
		for(String path:filesStrings) {
			File  filePathFile =new File(fileUrlString+path);
			if(filePathFile.isDirectory()) {
				doScannerClass(basePackage+"."+path);//com.enjoy.yuer
			}else {
				//.找到了 .class
				classNameLsList.add(basePackage+"."+filePathFile.getName());//com.enjoy.yuer.servlet.controller.YuerController.class
			}
		}

	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri =req.getRequestURI();// yuer-mvc/yuer/quesy
		String contextString =req.getContextPath();// yuer-mvc
		String path =  uri.replace(contextString, "");//yuer/query key
		Method methonMethod =(Method) handleMap.get(path);
		YuerController instance = (YuerController) beansMap.get("/"+path.split("/")[1]);
		Object[]  args =  hand(req,resp,methonMethod);//从底层调方法
		System.out.println("args:"+args);
		try {
			methonMethod.invoke(instance, args);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IllegalAccessException :"+e.getMessage());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IllegalArgumentException :"+e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("InvocationTargetException :"+e);
		}
		System.out.println("进行请求啦~~~~~~~~~doPost~~~~~~~~~~~~~~~~~~");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
		System.out.println("进行请求啦~~~~~~~~~doGet~~~~~~~~~~~~~");
	}



	private static Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method) {

		Class<?>[] paramClazzs = method. getParameterTypes();
		Object[] args = new Object[paramClazzs . length];

		int args_i = 0;
		int index= 0;

		for (Class<?> paramClazz : paramClazzs) {
			if (ServletRequest.class.isAssignableFrom(paramClazz)) {
				args[args_i++] = request;
			}  
			if (ServletResponse.class.isAssignableFrom(paramClazz)) {
				args[args_i++] = response;
			}

			Annotation[] paramAns = method.getParameterAnnotations()[index];
			if (paramAns.length > 0) {

				for(Annotation paramAn : paramAns){

					if (EnjoyRequestParam.class.isAssignableFrom(paramAn.getClass())){
						EnjoyRequestParam rp = (EnjoyRequestParam) paramAn; 
						args[args_i++] = request . getParameter(rp. value());
					}
				}
			
			}
			index++;
		}
		
		for (int i = 0; i < args.length; i++) {
			System.out.println("第 "+i+" 个"+args[i]);
		}
		
		return args;
	}

}