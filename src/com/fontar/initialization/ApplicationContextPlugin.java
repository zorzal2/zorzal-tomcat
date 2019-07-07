package com.fontar.initialization;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.pragma.app.AppContextUtil;

public class ApplicationContextPlugin implements  PlugIn {

	

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
	public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
		XmlWebApplicationContext context =  (XmlWebApplicationContext) servlet.getServletContext().getAttribute("org.springframework.web.struts.ContextLoaderPlugIn.CONTEXT.");
		AppContextUtil.setBeanFactory(context.getBeanFactory());
		InitializeApplicationService service = (InitializeApplicationService) context.getBean("initializationService");
		service.initializeApplication();
	}

}
