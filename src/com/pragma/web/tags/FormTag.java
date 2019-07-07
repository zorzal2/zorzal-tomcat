/**
 * 
 */
package com.pragma.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.strutsel.taglib.html.ELFormTag;

import com.pragma.util.StringUtil;
import com.pragma.web.NavigationManager;

/**
 * Extiende el tag form de html-el, agregándole la integración con el NavigationManager. De
 * esta manera en el submit de un form se envía la direccion de retorno precalculada.
 * @author llobeto
 * 
 */
public class FormTag extends ELFormTag {

	private static final long serialVersionUID = -473564916675297738L;

	public int doStartTag() throws JspException {
		
		int ret = super.doStartTag();
		
		String returnLocation = null;
		returnLocation = String.valueOf(pageContext.getRequest().getAttribute(NavigationManager.RETURN_LOCATION));
		if(StringUtil.isEmpty(returnLocation)) {
			returnLocation = pageContext.getRequest().getParameter(NavigationManager.RETURN_LOCATION);
		}
		if(StringUtil.isNotEmpty(returnLocation)) {
			try {
				pageContext.getOut().append("<input type=\"hidden\" name=\""+NavigationManager.RETURN_LOCATION+"\" value=\""+returnLocation+"\"></input>");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
}
