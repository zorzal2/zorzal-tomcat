/**
 *
 */
package com.pragma.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * @author fferrara Clase Util para mantener las referencias del contexto Web
 * desde cualquier parte de la aplicación es thread-safe.
 */
public class WebContextUtil {

	protected static ThreadLocal<HttpServletRequest> threadRequest = new ThreadLocal();

	protected static ThreadLocal<HttpServletResponse> threadResponse = new ThreadLocal();

	protected static ThreadLocal<HttpSession> threadSession = new ThreadLocal();

	protected static ThreadLocal<ServletContext> threadContext = new ThreadLocal();

	protected static ThreadLocal<Session> hibernateSessionInventarios = new ThreadLocal();

	protected static ThreadLocal<Session> jbpmSessionInventarios = new ThreadLocal();

	/**
	 * Almacena el request y response especificados
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 */
	public static void populate(HttpServletRequest req, HttpServletResponse res) {
		threadRequest.set(req);
		threadResponse.set(res);
		threadSession.set(req.getSession());
		threadContext.set(req.getSession().getServletContext());
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) threadRequest.get();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) threadResponse.get();
	}

	/**
	 * Devuelve la HttpSession
	 * @return HttpSession
	 */
	public static HttpSession getSession() {
		return (HttpSession) threadSession.get();
	}

	/**
	 * Devuelve el servlet context
	 * @return ServletContext
	 */
	public static ServletContext getContext() {
		return (ServletContext) threadContext.get();
	}

	/**
	 * Devuelve una Spring Bean Factorty según la configuración del
	 * ContextLoaderPlugIn, con esta Factory se pueden crear todos los Beans
	 * especificados en los archivos de configuración XML y referenciados en el
	 * ContextLoaderPlugIn del struts-config.xml
	 * @return BeanFactory
	 */
	public static XmlWebApplicationContext getBeanFactory() {
		return (threadContext.get() != null) ? (XmlWebApplicationContext) ((ServletContext) threadContext.get()).getAttribute("org.springframework.web.struts.ContextLoaderPlugIn.CONTEXT.")
				: null;
	}

	/**
	 * Setea la Hibernate Session para los inventarios, es el único caso donde
	 * se utiliza la session dentro del Controller. Se almacena aca para poder
	 * cerrarla adeacuadamente
	 * @param session
	 */
	public static void setHibernateSession(Session session) {
		hibernateSessionInventarios.set(session);
	}

	/**
	 * 
	 * @return Session
	 */
	public static Session getHibernateSession() {
		return hibernateSessionInventarios.get();
	}

	public static Session getJbpmSessionInventarios() {
		return jbpmSessionInventarios.get();
	}

	public static void setJbpmSessionInventarios(Session jbpmSessionInventarios) {
		WebContextUtil.jbpmSessionInventarios.set(jbpmSessionInventarios);
	}

	public static boolean hasJbpmSessionOpened() {
		return jbpmSessionInventarios.get() != null && jbpmSessionInventarios.get().isOpen();
	}

}
