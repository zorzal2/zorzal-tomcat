package com.fontar.web.decorator.link.impl;

import java.math.BigDecimal;


public class EditarMonto extends EditarLink {
	
	static private String IMG = "images/lapiz_small.gif";
	
	private static String getTarget(String url, String entidad, String propiedad, BigDecimal montoActual, String handlerBean){
		return (url + "?entidad=" + entidad + "&propiedad=" + propiedad + "&montoActual=" + montoActual.toString()+(handlerBean==null?"":("&handler="+handlerBean)));
	}
	
	public EditarMonto(String url, String description, Long id, String entidad, String propiedad, BigDecimal montoActual, String handlerBean){
		super(getTarget(url,entidad, propiedad, montoActual, handlerBean), description, id);
		this.openAsPopup();
		this.setSimplePermissionsRequired("SUPERUSUARIO");
	}	
	public EditarMonto(String url, String description, Long id, String entidad, String propiedad, BigDecimal montoActual){
		this(url, description, id, entidad, propiedad, montoActual, null);
	}
	
	protected String getImageSource() {
		return EditarMonto.IMG;
	}
}
