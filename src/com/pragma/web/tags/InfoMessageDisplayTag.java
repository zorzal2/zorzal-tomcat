/**
 * 
 */
package com.pragma.web.tags;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

import com.pragma.util.StringUtil;
import com.pragma.web.messages.InformationMessage;

/**
 * Muestra mensajes que no son de error o advertencia.
 * @author llobeto, fferrara
 * 
 */
public class InfoMessageDisplayTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private String showBody = null;

	public InfoMessageDisplayTag() {
		super();
	}

	public int doStartTag() throws JspException {

		ActionMessages messages;

		// levanto los Errores ActionMessages del request
		messages = TagUtils.getInstance().getActionMessages(pageContext, Globals.MESSAGE_KEY);

		MessageResources resources = MessageResources.getMessageResources("resources.InformationalMessages");

		if (messages != null && !messages.isEmpty() && !getShowBody().equalsIgnoreCase("true")) {


			StringBuilder result = buildHeader();

			for (Iterator iterator = messages.get(); iterator.hasNext();) {

				Object oMessage = iterator.next();

				// solo mensajes informativos propios de la aplicación
				if (oMessage instanceof InformationMessage) {

					InformationMessage infoMessage = (InformationMessage) oMessage;

					// puede venir una key de un bundle o un mensaje

					String message;
					if (infoMessage.isResource()) {
						if (infoMessage.getValues() != null) {
							message = resources.getMessage(infoMessage.getKey(), infoMessage.getValues());
						}
						else {
							message = resources.getMessage(infoMessage.getKey());
						}
					} else {
						message = infoMessage.getKey();
					}
					result.append(TagUtils.getInstance().filter(message));

					// Separo los errores con newline
					if (iterator.hasNext()) {
						result.append("<br/>");
					}
				}
			}
			this.setValue("doEnd", true);

			try {
				pageContext.getOut().print(result.toString());
			}
			catch (IOException e) {
				e.printStackTrace();
				throw new JspException(e);
			}
		} else {
			this.setValue("doEnd", false);
		}
		if(getShowBody().equalsIgnoreCase("true")) {
			this.setValue("doEnd", true);
			try {
				pageContext.getOut().print(buildHeader());
			}
			catch (IOException e) {
				e.printStackTrace();
				throw new JspException(e);
			}
			return EVAL_BODY_INCLUDE;
		}
		else return SKIP_BODY;
	}

	private StringBuilder buildHeader() {
		String id = "InfoMessage_"+Math.round(Math.random()*10000.0);
		StringBuilder result = new StringBuilder();
		this.setValue("InfoMessage_id", id);
		result.append("<div  id=\""+id+"\">\n");
		result.append("<br/>");
		result.append("<table class=\"InfoMessage\">\n<tr>\n");
		result.append("<td class=\"text\">");
		return result;
	}

	public int doEndTag() throws JspException {
		boolean doEnd = (Boolean) this.getValue("doEnd");
		if(doEnd) {
			StringBuilder result = new StringBuilder();
			String id =(String) this.getValue("InfoMessage_id");
			
			result.append("</td>\n");
			result.append("<td class=\"close\"><span onclick=\"document.getElementById('"+id+"').style.display='none'\">&nbsp;</span></td>\n");
			result.append("</tr></table>\n");
			result.append("</div>\n");
	
			try {
				pageContext.getOut().print(result.toString());
			}
			catch (IOException e) {
				e.printStackTrace();
				throw new JspException(e);
			}
		}
		return super.doEndTag();
	}

	public String getShowBody() {
		if(StringUtil.isEmpty(showBody)) return "false";
		return showBody;
	}

	public void setShowBody(String showBody) {
		this.showBody = showBody;
	}
}
