package com.fontar.web.decorator.link.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;

public class CargarProyectoLink  extends BaseFontarObjectTargetLink {

	static private final String IMG = "images/cargarproyecto.gif";

	private String tipoProyecto;
	
	private Long idPresentacion; 
	
	public CargarProyectoLink(String target, String description, Long id) {
		super(target, description, id);
	}

	public String getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(String tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}
	

	public Long getIdPresentacion() {
		return idPresentacion;
	}

	public void setIdPresentacion(Long idPresentacion) {
		this.idPresentacion = idPresentacion;
	}

	@Override
	protected String getImageSource() {
		return CargarProyectoLink.IMG;
	}
	
	public String displayValueImpl() throws UnsupportedEncodingException {
		StringBuffer link = new StringBuffer();
		link.append("<a href=\"");
		link.append(this.getTarget());
		link.append(this.getTarget().contains("?")? '&':'?');
		
		if(this.idPresentacion!=null){
			link.append("idPresentacion=");
			link.append( URLEncoder.encode(String.valueOf(this.getIdPresentacion()), "UTF-8"));
			link.append("&tipoProyecto=PresentacionConvocatoria");
		}else{
			link.append("id=");
			link.append( URLEncoder.encode(String.valueOf(this.getId()), "UTF-8"));	
		}
		link.append("\"><img class=\"imageButton\" src='");
		link.append(getImageSource());
		link.append("' title=");
		link.append("'" + this.getDescription() + "' border=0 /></a>");
		return link.toString();
	}

}
