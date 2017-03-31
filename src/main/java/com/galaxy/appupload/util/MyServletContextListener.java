package com.galaxy.appupload.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class MyServletContextListener implements ServletContextListener {
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public void contextInitialized(ServletContextEvent arg0) {
		//读取配置文件
		ReadProperties.Init();
	}
 
}
