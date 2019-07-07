package com.pragma.web.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.seguridad.AuthorizationService;
import com.fontar.seguridad.cripto.AccesoDenegadoException;
import com.fontar.util.Util;
import com.pragma.PragmaException;
import com.pragma.util.ContextUtil;
import com.pragma.web.NavigationManager;
import com.pragma.web.messages.ErrorMessage;
import com.pragma.web.messages.InformationMessage;

/**
 * Action base de todas las MappingDispatchAction que se creen Loguea
 * información antes y después de la ejecución de la acción.
 * 
 * @author fferrara
 */
public abstract class BaseMappingDispatchAction extends MappingDispatchAction {
	
	protected static final String FORWARD_SUCCESS = "success";
	protected static final String FORWARD_INVALID = "invalid";
	protected static final String FORWARD_BANDEJA_ENTRADA = "bandejaDeEntrada";
	protected final CollectionHandler collectionHandler = new CollectionHandler();

	public static final String NAVIGATION_OVERRIDE_FORWARD = "fontar.navigation.override";
	
	/**
	 * Override de execute para correr el loguer antes y después de la misma
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logEntry(mapping, form, request, response);
		ActionForward forward = super.execute(mapping, form, request, response);
		logExit(mapping, form, request, response, forward);
		return forward;
	}

	/**
	 * Log anterior a la ejecución de la acción
	 */
	protected void logEntry(ActionMapping mapping, ActionForm baseForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log log = LogFactory.getLog(this.getClass().getName());
		if (log.isInfoEnabled()) {
			log.info("Entrando a " + this.getClass().getName());
		}
		// solo en modo DEBUG muestro el valor de los parametros
		if (log.isDebugEnabled()) {
			log.debug("Request hashCode:" + request.hashCode());

			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String s = (String) e.nextElement();
				String param = request.getParameter(s);
				log.debug("Request Parameter:" + s + ",Value:" + param);
			}
		}
	}

	/**
	 * Log posterior a la ejecución de la acción
	 */
	protected void logExit(ActionMapping mapping, ActionForm baseForm, HttpServletRequest request,
			HttpServletResponse response, ActionForward forward) {
		Log log = LogFactory.getLog(this.getClass().getName());
		try {
			if (log.isInfoEnabled()) {
				log.info("Saliendo de " + this.getClass().getName());
				log.info("Dispatching to " + forward.getPath());
			}
		}
		catch (Exception e) {
			log.error(e.getClass().getName() + " ocurrió. Es ignorado por se generado por el log4j");
		}
	}
	
	protected void addError(ActionMessages messages, String bundlekey, Object... params ) {
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ErrorMessage(bundlekey, params));
	}
	
	protected void addError(ActionMessages messages, String bundlekey) {
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ErrorMessage(bundlekey, true));
	}
	
	protected void addLiteralError(ActionMessages messages, String messageText) {
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ErrorMessage(messageText, false));
	}
	
	protected void addError(ActionMessages messages, PragmaException ex) {
		if (ex.getBundleKey() != null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ErrorMessage(ex.getBundleKey(), ex.getParam()));
		}
		else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ErrorMessage(ex.getMessage(), false));
		}
	}

	protected void addInformationMessage(ActionMessages messages, String bundlekey, Object param ) {
		messages.add(ActionMessages.GLOBAL_MESSAGE, new InformationMessage(bundlekey, param));
	}
	
	protected void addInformationMessage(ActionMessages messages, String bundlekey, Object[] params ) {
		messages.add(ActionMessages.GLOBAL_MESSAGE, new InformationMessage(bundlekey, params));
	}
	
	protected void addLiteralInformationMessage(ActionMessages messages, String messageText) {
		messages.add(ActionMessages.GLOBAL_MESSAGE, new InformationMessage(messageText, false));
	}
	
	protected void addInformationMessage(ActionMessages messages, String bundlekey) {
		messages.add(ActionMessages.GLOBAL_MESSAGE, new InformationMessage(bundlekey, true));
	}

	protected void addInformationMessage(ActionMessages messages, PragmaException ex) {
		if (ex.getBundleKey() != null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new InformationMessage(ex.getBundleKey(), ex.getParam()));
		}
		else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new InformationMessage(ex.getMessage(), false));
		}
	}
	
	/**
	 * Valida que el parametro exista en el request
	 * @param parameterName
	 */ 
	protected boolean validateParameter(HttpServletRequest request ,String parameterName){
		String parameterValue = request.getParameter(parameterName);
		return !Util.isBlank(parameterValue);
	}
	
	 protected void setForward(HttpServletRequest request, String forward){
		 this.setForward(request,NAVIGATION_OVERRIDE_FORWARD,forward);
	 }
	
	 
	 protected void setForward(HttpServletRequest request, String forwardKey, String forward){
		 request.getSession().setAttribute(forwardKey,forward);
	 }
	 
	 
	 protected void overrideForward(HttpServletRequest request){
		 this.setForward(request, NavigationManager.getPreviousURI(request));
	 }	 
	 /**
	  * Chequea que los permisos esten dados para ejecutar la accion.
	  * Dispara una excepcion de AccesoDenegado si el usuario no tiene
	  * los permisos adecuados para realizar la accion.
	  * @param idInstrumento
	  * @param permissions
	  * @throws AccesoDenegadoException 
	  */
	 protected void verifyGranted(Long idInstrumento, String... permissions) throws AccesoDenegadoException {
		 AuthorizationService authorizationService = (AuthorizationService)ContextUtil.getBean("authorizationService");
		 for (String permission : permissions) {
			 if(!authorizationService.grantedPermissionByInstrumento(permission, idInstrumento))
				 throw new AccesoDenegadoException("Acceso denegado");
		 }
	 }
	 /**
	  * Chequea que los permisos esten dados para ejecutar la accion.
	  * Dispara una excepcion de AccesoDenegado si el usuario no tiene
	  * los permisos adecuados para realizar la accion.
	  * @param permissions
	 * @throws AccesoDenegadoException 
	  */
	 protected void verifyGranted(String... permissions) throws AccesoDenegadoException {
		 AuthorizationService authorizationService = (AuthorizationService)ContextUtil.getBean("authorizationService");
		 for (String permission : permissions) {
			 if(!authorizationService.grantedSimplePermission(permission))
				 throw new AccesoDenegadoException("Acceso denegado");
		}
	 }
	 
}