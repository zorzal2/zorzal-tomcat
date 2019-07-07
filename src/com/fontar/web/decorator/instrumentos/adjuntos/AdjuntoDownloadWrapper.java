package com.fontar.web.decorator.instrumentos.adjuntos;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.el.ELException;

import com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean;
import com.fontar.data.impl.domain.codes.llamadoConvocatoria.EstadoLlamadoConvocatoria;
import com.fontar.data.impl.domain.dto.AdjuntoDTO;
import com.fontar.util.ResourceManager;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.BorrarLink;
import com.fontar.web.decorator.link.impl.LinkJS;

public class AdjuntoDownloadWrapper extends TableDecoratorSupport {

	public String getActions() {
		AdjuntoDTO dto = (AdjuntoDTO) this.getCurrentRowObject();
		StringBuffer html = new StringBuffer();
		
		html		
		.append("<img src='images/download.gif' style='cursor:hand;'  border=0 alt=")
		.append("'"+ ResourceManager.getAltResource("app.alt.downloadAdjuntos") + "'")
		.append("' onClick=\"onDownload('")
		.append(dto.getIdAdjuntoContenido())
		.append("','")
		.append(dto.getNombre())
		.append("','")
		.append(dto.getTipoContenido())		
		.append("')\"/>");		

		return  html.toString();
	}
	
	public String getSize(AdjuntoDTO dto){
		long size = dto.getCantidadLongitud().longValue();
		String unit = "B";
		if(size>1024) {
			size=size/1024;
			unit = "KB";
			if(size>1024) {
				size=size/1024;
				unit = "MB";
			}
		} 
		return Long.toString(size)+unit; 
	}

	public String getFileDesc() {
		AdjuntoDTO dto = (AdjuntoDTO) this.getCurrentRowObject();
		return "<B>"+dto.getNombre()+"</B>&nbsp;("+getSize(dto)+")<BR/>"+dto.getDescripcion();  
	}

	public String getBorrarAdjunto(){
		AdjuntoDTO dto = (AdjuntoDTO) this.getCurrentRowObject();
		String permisoJSP = "";
		try {
			permisoJSP = getPageContext().getExpressionEvaluator().evaluate("${param.permisoParaAgregar}", java.lang.String.class, getPageContext().getVariableResolver(), null).toString();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		LinkJS borrarLinkJS = new LinkJS("onDelete(\"" + dto.getId() + "\")", "app.alt.borrarAdjuntos","images/btnEliminar.gif");
		
		if (permisoJSP.length() > 0){
			String permisos[] = permisoJSP.split(",");
			borrarLinkJS.setSimplePermissionsRequired(permisos);
		}
		return borrarLinkJS.displayValue();
		/*
		StringBuffer html = new StringBuffer();
		html
			.append("<img src='' style='cursor:hand;' border=0 alt=")
			.append("'"+ ResourceManager.getAltResource("app.alt.borrarAdjuntos") + "'")
			.append("' onClick='onDelete(\"")
			.append(dto.getId())
			.append("\")'/>");

		return html.toString();
		*/
		
	}
	
	
}
