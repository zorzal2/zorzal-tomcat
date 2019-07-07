/**
 * 
 */
package com.pragma.web;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;

/**
 * @author fferrara Action Servlet que puebla las variables del WebContextUtil
 */
public class PragmaActionServlet extends ActionServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		// TODO Auto-generated method stub
		doPost(arg0, arg1);
	}
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		Locale.setDefault(new Locale("ES", "ar"));
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		feedAccessClass(req, resp);
		resp.setHeader("PRAGMA","NO-CACHE");
		resp.setHeader("EXPIRES","-1");
		super.doPost(req, resp);
		cleanUp();
	}

	private void feedAccessClass(HttpServletRequest req, HttpServletResponse resp) {
		WebContextUtil.populate(req, resp);
	}

	private void cleanUp() {
		// close Hibernate Session for Inventarios
		if (WebContextUtil.getHibernateSession() != null) {
			WebContextUtil.getHibernateSession().close();
			WebContextUtil.setHibernateSession(null);
		}

		// close Jbpm Hibernate Session for Inventarios
		if (WebContextUtil.getJbpmSessionInventarios() != null) {
			WebContextUtil.getJbpmSessionInventarios().close();
			WebContextUtil.setJbpmSessionInventarios(null);
		}
	}

}
