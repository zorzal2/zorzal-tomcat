package com.fontar.web.seguridad;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class ActionAuthorizeTag extends  TagSupport {

	private static final long serialVersionUID = -9206488318760850760L;
	
	private Boolean permission;
	
	public Boolean getPermission() {
		return permission;
	}

	public void setPermission(Boolean permission) {
		this.permission = permission;
	}

	public int doStartTag() throws JspException {
		return this.permission? Tag.EVAL_BODY_INCLUDE : Tag.SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return super.doEndTag();
	}
}
