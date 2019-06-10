package com.enjoy.yuer.contorller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enjoy.yuer.annnotaiton.EnjoyAutowired;
import com.enjoy.yuer.annnotaiton.EnjoyController;
import com.enjoy.yuer.annnotaiton.EnjoyRequestMapping;
import com.enjoy.yuer.annnotaiton.EnjoyRequestParam;
import com.enjoy.yuer.server.YuerService;

@EnjoyController
@EnjoyRequestMapping("/yuer")
public class YuerController {
	//×¢²áÈç
	@EnjoyAutowired("YuerServletImpl")
	private YuerService yuerService;  
	
	
	@EnjoyRequestMapping("/query")
	public void query(HttpServletRequest request,HttpServletResponse response, 
			@EnjoyRequestParam("name") String name,@EnjoyRequestParam("age") String age) {
		String result = yuerService.query(name, age);
		PrintWriter printWriter ;
		try {
			printWriter =response.getWriter();
			printWriter.write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//printWriter.write("Errpo");
			e.printStackTrace();
		}
	}
}
