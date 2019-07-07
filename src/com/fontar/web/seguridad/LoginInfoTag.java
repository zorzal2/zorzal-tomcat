package com.fontar.web.seguridad;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.ui.AbstractProcessingFilter;

public class LoginInfoTag extends  TagSupport {

	private static final long serialVersionUID = 8697694133383429944L;
	
	public int doStartTag() throws JspException {
		
		try {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("<p>El intento de login ha fallado, intente nuevamente.</p>");
			stringBuffer.append("<p>");
			stringBuffer.append("Motivo:");
			stringBuffer.append(this.getCause());
			pageContext.getOut().print(stringBuffer);
		}catch (IOException e) {
			throw new JspException(e);
		}
		return  Tag.SKIP_BODY;
	}

	
	private String getCause() {
		AuthenticationException authenticationException  = (AuthenticationException) this.pageContext.getSession().getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY);
		try{
			throw authenticationException;
		}catch(BadCredentialsException e){
			return "Usuario/Contraseña incorrecta";
		}catch (AuthenticationException e) {
			return "Servicio no disponible";
		}
	}


	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

}
