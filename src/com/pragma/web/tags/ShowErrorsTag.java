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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;

/**
 * @author fferrara
 * 
 */
public class ShowErrorsTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -473564916675297738L;

	public ShowErrorsTag() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int doStartTag() throws JspException {

		ActionMessages errors;

		// levanto los ActionMessages del request
		errors = TagUtils.getInstance().getActionMessages(pageContext, Globals.ERROR_KEY);

		if (errors != null) {

			if (!errors.isEmpty()) {
				JspWriter writer = pageContext.getOut();

				StringBuffer result = new StringBuffer();

				// javascript con alert
				result.append("<script>");
				result.append("alert(\"");
				// APResourceBLogic apr = new APResourceBLogic();
				for (Iterator iterator = errors.get(); iterator.hasNext();) {
					ActionMessage error = (ActionMessage) iterator.next();

					// agrego el mensaje de error
					if (error.isResource()) {

						String descripcion = error.getKey(); // apr.getAPresources(error.getKey());
						result.append(TagUtils.getInstance().filter(descripcion));
					}
					else {
						result.append(TagUtils.getInstance().filter(error.getKey()));
					}
					// Separo los errores con newline
					if (iterator.hasNext()) {
						result.append("\\n");
					}
				}
				// cierro el bloque javascript
				result.append("\");");
				result.append("</script>");
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
