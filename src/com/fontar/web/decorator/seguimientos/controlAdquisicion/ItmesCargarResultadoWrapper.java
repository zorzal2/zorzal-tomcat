package com.fontar.web.decorator.seguimientos.controlAdquisicion;

import java.util.Date;

import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.EditarJavaScriptLink;
import com.pragma.util.StringUtil;

/**
 * Wrapper para items de pac.<br>
 * @author ssanchez
 */
public class ItmesCargarResultadoWrapper extends TableDecoratorSupport {

	public String getLinkEditarResultadoFontar() {
		ProcedimientoItemBean bean = (ProcedimientoItemBean) this.getCurrentRowObject();
		String link = "";
		
		EditarJavaScriptLink editarLink = new EditarJavaScriptLink("popUpEditarResultadoFontar", "app.alt.editarResultado", bean.getId());
		link = editarLink.displayValue(); 
		
		return link;		
	}
	
	public String getLinkEditarResultadoUffa() {
		ProcedimientoItemBean bean = (ProcedimientoItemBean) this.getCurrentRowObject();
		String link = "";
		
		EditarJavaScriptLink editarLink = new EditarJavaScriptLink("popUpEditarResultadoUffa", "app.alt.editarResultado", bean.getId());
		link = editarLink.displayValue(); 
		
		return link;		
	}

	public String getLinkEditarResultadoBid() {
		ProcedimientoItemBean bean = (ProcedimientoItemBean) this.getCurrentRowObject();
		String link = "";
		
		EditarJavaScriptLink editarLink = new EditarJavaScriptLink("popUpEditarResultadoBid", "app.alt.editarResultado", bean.getId());
		link = editarLink.displayValue(); 
		
		return link;		
	}	
	public String getFecha() {
		ProcedimientoItemBean bean = (ProcedimientoItemBean) this.getCurrentRowObject();
		String formatedFecha = "";
		
		Date fecha = bean.getFecha();
		
		if(fecha!=null) {
			formatedFecha = StringUtil.formatDate(fecha);
		}
		
		return formatedFecha;		
	}
	
	public String getMoneda() {
		ProcedimientoItemBean bean = (ProcedimientoItemBean) this.getCurrentRowObject();
		
		String moneda = bean.getMoneda()!=null ? bean.getMoneda().getDescripcion() : null;
		
		return moneda;		
	}	
}
