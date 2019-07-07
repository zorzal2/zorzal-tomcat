/**
 * 
 */
package com.pragma.web.tags;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

import com.pragma.web.messages.ErrorMessage;

/**
 * @author fferrara
 * 
 */
public class ErrorDisplayTag extends TagSupport {

	private static final long serialVersionUID = -473564916675297738L;

	public ErrorDisplayTag() {
		super();
	}

	public int doStartTag() throws JspException {

		ActionMessages errors;

		// levanto los Errores ActionMessages del request
		errors = TagUtils.getInstance().getActionMessages(pageContext, Globals.ERROR_KEY);

		//Locale locale = TagUtils.getInstance().getUserLocale(pageContext, null);
		MessageResources resources = MessageResources.getMessageResources("resources.ErrorMessages");

		if (errors != null) {

			if (!errors.isEmpty()) {
				JspWriter writer = pageContext.getOut();

				StringBuffer result = new StringBuffer();
				result.append("<span class='error'>");

				for (Iterator iterator = errors.get(); iterator.hasNext();) {

					Object error = iterator.next();

					// solo mensajes de error propios de la aplicación
					if (error instanceof ErrorMessage) {

						ErrorMessage errorMessage = (ErrorMessage) error;

						// puede venir una key de un bundle o un mensaje

						String message = "";
						if (errorMessage.isResource()) {
							if (errorMessage.getValues() != null) {
								message = resources.getMessage(errorMessage.getKey(), errorMessage.getValues());
							}
							else {
								message = resources.getMessage(errorMessage.getKey());
							}
						}
						else {
							message = errorMessage.getKey();
						}

						if(message.startsWith("#html:")) {
							result.append(message.substring(6));
						} else {
							result.append(TagUtils.getInstance().filter(message));
						}

						// Separo los errores con newline
						if (iterator.hasNext()) {
							result.append("<br/>");
						}
					}
				}
				// cierro el bloque
				result.append("</span>");

				try {
					writer.print(result.toString());
				}
				catch (IOException e) {
					throw new JspException(e);
				}
			}
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return super.doEndTag();
	}
}
