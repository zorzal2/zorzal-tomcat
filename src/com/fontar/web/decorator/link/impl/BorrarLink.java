package com.fontar.web.decorator.link.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.util.ResourceManager;
import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;


public class BorrarLink extends BaseFontarObjectTargetLink {
	private static final String INLINE_ACTIONS = "inlineActions";

	static private final String IMG = "images/eliminar.gif";
	
	private String targetWindow = INLINE_ACTIONS;
	
	public BorrarLink(String target, String descriptionCode, Long id) {
		super(target, descriptionCode, id);
	}

	public BorrarLink(String target, String descriptionCode, String id) {
		super(target, descriptionCode, id);
	}

	@Override
	protected String getImageSource() {
		return IMG;
	}

	@Override
	public String displayValueImpl() throws UnsupportedEncodingException {
		StringBuilder link = new StringBuilder();
		
		String url = 	this.getTarget()
					+	(this.getTarget().contains("?")? '&':'?')
					+	"id="
					+	URLEncoder.encode(this.getId(), "UTF-8");
		
		link.append("<a href=\"");
		link.append(url);
		link.append("\" ");
		link.append(targetWindow==null ? "" : "target=\""+targetWindow+"\"");
		link.append(" onclick=\"return confirm('");
		link.append(ResourceManager.getInformationalResource("app.msj.confirmaEliminacion"));
		link.append("')\"><img class=\"imageButton\" src='");
		link.append(getImageSource());
		link.append("' title=");
		link.append("'" + this.getDescription() + "' border=0 /></a>");
		return link.toString();		
	}
	
	public void openInCurrentWindow() {
		targetWindow = null;
	}
	/**
	 * Abre el link en una nueva ventana flotante (Default).
	 *
	 */
	public void openInResultWindow() {
		targetWindow = INLINE_ACTIONS;
	}
}