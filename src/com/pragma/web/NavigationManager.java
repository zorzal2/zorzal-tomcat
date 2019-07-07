package com.pragma.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Manager de la pila de navegación
 * @author fferrara
 */
public class NavigationManager {

	private static String STACK_ID = "fontar.navigation.stack";
	
	public static String RETURN_LOCATION = "fontar.navigation.return";
	
	private  static String defaultLocation = null;
	
	/**
	 * Pone en el request la informacion necesaria para retroceder a la url previa y
	 * pone esta url como checkpoint al que se puede volver luego.
	 * @param request
	 */	
	public static void proccessStackableAction(HttpServletRequest request){
		
		// get stack from session
		NavigationStack navigationStack = (NavigationStack) request.getSession().getAttribute(STACK_ID);
		
		// init stack
		if (navigationStack == null) {
			navigationStack = new NavigationStack(defaultLocation);
			if(request.getParameter(RETURN_LOCATION)!=null) {
				request.setAttribute(RETURN_LOCATION, request.getParameter(RETURN_LOCATION));
			}
		} else {
			//Pongo la direccion de retorno que hubiera
			if(request.getParameter(RETURN_LOCATION)!=null) {
				request.setAttribute(RETURN_LOCATION, request.getParameter(RETURN_LOCATION));
			} else {
				request.setAttribute(RETURN_LOCATION, navigationStack.topAction());
			}
		}
		// push current RequestURI into Stack
		
		String servletPath = request.getServletPath(); 
		
		if (request.getQueryString() != null){
			servletPath += "?";
			servletPath += request.getQueryString();
		}
		
		// Decisión sobre apilar una acción o no en la pila de navegación.
		// (cualquier valor de "strPush" distinto del string "no" provoca la apilación de la acción)
		String strPush = request.getParameter("push");
		boolean push = strPush == null || !strPush.equals("no") || !strPush.equals("false");
		
		if (push) {
			navigationStack.pushAction(servletPath);	
			// set stack to session
			request.getSession().setAttribute(STACK_ID,navigationStack);
		}		
	}
	/**
	 * Pone en el request la informacion necesaria para retroceder al último
	 * checkpoint.
	 * @param request
	 */
	public static void proccessNonStackableAction(HttpServletRequest request){
		
		if(request.getParameter(RETURN_LOCATION)!=null) {
			request.setAttribute(RETURN_LOCATION, request.getParameter(RETURN_LOCATION));
		} else {
			NavigationStack navigationStack = (NavigationStack) request.getSession().getAttribute(STACK_ID);
			if(navigationStack==null) {
				request.setAttribute(RETURN_LOCATION, defaultLocation);
			} else {
				request.setAttribute(RETURN_LOCATION, navigationStack.topAction());
			}
		}
	}
	
	
	public static ActionForward getPreviousAction(HttpServletRequest request, String previousActionKey){
		
		ActionForward actionForward = new ActionForward();
		String overrideForward = (String) request.getSession().getAttribute(previousActionKey);
		if(overrideForward!=null){
			request.getSession().removeAttribute(previousActionKey);
			actionForward.setPath(overrideForward);
			actionForward.setRedirect(true);
			return actionForward;
		}
		
		return getPreviousAction(request);
	}
	
	
	/**
	 * Obtiene la localizacion de retorno a partir del request.
	 * @param request
	 * @return
	 */
	public static ActionForward getPreviousAction(HttpServletRequest request){
		ActionForward af = new ActionForward();
		af.setPath(getPreviousURI(request));
		af.setRedirect(false);
		
		return af;
	}
	
	public static String getPreviousURI(HttpServletRequest request){
		//tiene prioridad la direccion de retorno que figura en el request
		if(request.getParameter(RETURN_LOCATION)!=null) {
			return request.getParameter(RETURN_LOCATION);
		} else {
			NavigationStack navigationStack = (NavigationStack) request.getSession().getAttribute(STACK_ID);
			if (navigationStack == null) {
				return null;
			}
			if (navigationStack.isEmpty()){
				return null;
			}
			return navigationStack.popAction();
		}
	}

	/**
	 * Devuelve el previous URI eliminando el "/" incial para que Struts tome el App name en el URI
	 * @param request
	 * @return
	 */
	public static String getPreviousURIHref(HttpServletRequest request){
		String requestURI = NavigationManager.getPreviousURI(request); 
		return requestURI.substring(1);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getPreviousURIHref(HttpServletRequest request, String previousAction){		
		
		String overrideForward = (String) request.getSession().getAttribute(BaseMappingDispatchAction.NAVIGATION_OVERRIDE_FORWARD);
		// request.getSession().getAttribute(BaseMappingDispatchAction.NAVIGATION_OVERRIDE_FORWARD)
		String requestURI = "";
		
		if(overrideForward!=null){
			requestURI = overrideForward;
		}
		else {
			requestURI = NavigationManager.getPreviousURI(request);	
		}
		
		return requestURI.startsWith("/")? requestURI.substring(1,requestURI.length()) : requestURI;
	}
	
	public static String requestedPath(HttpServletRequest request){
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append( request.getServletPath() );
		if (request.getQueryString() != null){
			stringBuffer.append("?");
			stringBuffer.append(request.getQueryString());
		}
	
		return stringBuffer.toString();
	}
	
	public static void saveCurrentLocationWithName(HttpServletRequest request, String uri, String name) {
		request.getSession().setAttribute(name, uri);
	}
	
	public static void saveCurrentLocationWithName(HttpServletRequest request, String name) {
		request.getSession().setAttribute(name, requestedPath(request));
	}
	public static ActionForward getSavedLocation(HttpServletRequest request, String name) {
		String uri = (String)request.getSession().getAttribute(name);
		if(uri==null) return null;
		
		ActionForward af = new ActionForward();
		
		af.setPath(uri);
		af.setRedirect(true);
		
		return af;
	}


	public static String getDefaultLocation() {
		return defaultLocation;
	}


	public static void setDefaultLocation(String defaultLocation) {
		if("".equals(defaultLocation)) NavigationManager.defaultLocation = null;
		else NavigationManager.defaultLocation = defaultLocation;
	}
}