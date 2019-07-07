package com.fontar.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;

import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.pragma.util.ContextUtil;
import com.pragma.web.messages.ErrorMessage;
import com.pragma.web.messages.InformationMessage;

public class ActionUtil {
	/**
	 * Determina si hay contexto de encriptaci�n. Devuelve true si el usuario ingres� la
	 * clave de encritaci�n y false si no.
	 * @return
	 */
	public static boolean isEncryptionContextAvailable(){
		return ((FontarCryptographicService) ContextUtil.getBean("cryptographicService")).encyptionAvailable();
	}
	/**
	 * Verifica que haya contexto de encriptaci�n. Si no lo hay deja un mensaje en el objeto
	 * ActionMessages dado.
	 * @param request
	 * @param messages
	 */
	public static void alertForEncription(HttpServletRequest request, ActionMessages messages) {
		//Agrego un mensaje de advertencia si no hay contrase�a de encriptacion
		if (!isEncryptionContextAvailable()){
			addInformationMessage(messages, "app.msj.debeIngresarContraseniaParaContinuar");
		}
	}
	/**
	 * Agrega un mensaje informativo a la coleccion de mensajes dada.
	 * @param messages
	 * @param bundlekey
	 */
	public static void addInformationMessage(ActionMessages messages, String bundlekey) {
		messages.add(ActionMessages.GLOBAL_MESSAGE, new InformationMessage(bundlekey, true));
	}
	/**
	 * Verifica que haya un contexto de encriptaci�n. Si lo hay, devuelve true. Si no, devuelve
	 * false y pone un mensaje de error en la coleccion de mensajes dada.
	 * @param messages
	 * @return
	 */
	public static boolean checkValidEncryptionContext(ActionMessages messages){
		if (!isEncryptionContextAvailable()){
			addError(messages,"app.error.encrypt");
			return false;
		}
		return true;
	}
	/**
	 * Agrega el mensaje de error definido con la clave <code>bundleKey</code> a la 
	 * colecci�n de mensajes dada.
	 * @param messages
	 * @param bundlekey
	 */
	public static void addError(ActionMessages messages, String bundlekey) {
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ErrorMessage(bundlekey, true));
	}
}
