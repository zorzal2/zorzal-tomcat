package com.fontar.web.seguridad;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.pragma.util.ContextUtil;



public class AuthorizationTag extends TagSupport {

	
	private static final long serialVersionUID = 1L;
	
	
	private String permissions;
	

	public AuthorizationTag(){
		super();
	}
	

	public String getPermissions() {
		return permissions;
	}


	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String[] getPermissionsRequired(){
		return this.getPermissions().split(",");
	}
	
	public AuthorizationTagAccessController getAccessController(){
		return (AuthorizationTagAccessController) ContextUtil.getBean("authorizationTagAccessController");
	}

	/**
	 * Controla que el usuario tenga los permisos requeridos para acceder al contentido
	 **/
	private Boolean accessAlloed(){
		return this.getAccessController().decide( this );
	}
	
	/**
	 * Si el usuario tiene los permisos adecuados se evalua el contenido del tag
	 * */
	public int doStartTag() throws JspException {
		return this.accessAlloed()? Tag.EVAL_BODY_INCLUDE : Tag.SKIP_BODY;
	}

	
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}
}
