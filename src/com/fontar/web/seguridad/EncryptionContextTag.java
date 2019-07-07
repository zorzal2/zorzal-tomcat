package com.fontar.web.seguridad;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.fontar.web.decorator.link.impl.EncryptionLink;
import com.pragma.util.ContextUtil;

public class EncryptionContextTag extends  TagSupport {

	
	private static final long serialVersionUID = 6294114594228979016L;

	
	private boolean encryptionAvailable(){
		FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
		return service.encyptionAvailable();
	}
	
	public int doStartTag() throws JspException {
		
		try {
			JspWriter writer = pageContext.getOut();
			EncryptionLink encryptionLink = new EncryptionLink(this.encryptionAvailable());
			writer.print(encryptionLink.displayValue());
		}catch (IOException e) {
			throw new JspException(e);
		}
		return  Tag.SKIP_BODY;
	}

	
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}
}
