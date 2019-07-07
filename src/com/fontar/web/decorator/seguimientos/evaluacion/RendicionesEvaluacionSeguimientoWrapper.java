package com.fontar.web.decorator.seguimientos.evaluacion;

import static com.fontar.data.Constant.ACTION_AUTHORIZE;

import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.dto.RendicionCuentasDTO;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.Util.EditableMoney;
import com.fontar.web.decorator.link.impl.EditarJavaScriptLink;
import com.fontar.web.decorator.link.impl.EditarMonto;

/**
 * Wrapper para las <code>Rendiciones</code> de las
 * <code>Evaluaciones de Seguimiento</code>.<br> 
 * Sobreescribe los montos y observaciones asignandoles 
 * un id para que el popup pueda refrescar 
 * sus valores.<br>
 * @author ssanchez
 */


// Wrapper Deprecado ?

public class RendicionesEvaluacionSeguimientoWrapper extends TableDecoratorSupport {
	
	public String getLinkEditarMontosANR() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		String link = "";
		
		//según desde la accion que haya sido invocada se muestra o no el link
		if ((Boolean)getPageContext().getSession().getAttribute(ACTION_AUTHORIZE)) {
			EditarJavaScriptLink editarLink = new EditarJavaScriptLink("popUpEditarMontosRendicionEvaluacionSeguimientoANR", "app.alt.editarMontos", bean.getId());
			link = editarLink.displayValue(); 
		}
		
		return link;		
	}
	public String getLinkEditarMontosCF() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		String link = "";
		
		//según desde la accion que haya sido invocada se muestra o no el link
		if ((Boolean)getPageContext().getSession().getAttribute(ACTION_AUTHORIZE)) {
			EditarJavaScriptLink editarLink = new EditarJavaScriptLink("popUpEditarMontosRendicionEvaluacionSeguimientoCF", "app.alt.editarMontos", bean.getId());
			link = editarLink.displayValue(); 
		}
		
		return link;		
	}

	
	public EditableMoney getMontoTotalEvaluacion(){
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAprobado",bean.getId() ,"RendicionCuentas", "montoContraparteEvaluacion", bean.getMontoTotalEvaluacion());
		return (new EditableMoney(bean.getMontoTotalEvaluacion(), editarMonto));
	}
	
	
	//TODO: SS-quedo comentado para futura implementación. Se usaba para no hacer 
	// reload de la pagina llamadora cada vez q cambia el popup
//	public String getMontoParteEvaluacion() {
//		DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
//		RendicionCuentasBean bean= (RendicionCuentasBean)this.getCurrentRowObject();
//		
//		StringBuffer link = new StringBuffer();
//		
//		link.append("<span id=\"montoParteEvaluacion");
//		link.append(bean.getId());
//		link.append("\">");
//		link.append(twoPlaces.format(bean.getMontoParteEvaluacion()));
//		link.append("</span>");
//
//		return link.toString();
//	}
//	
//	public String getMontoContraparteEvaluacion() {
//		DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
//		RendicionCuentasBean bean= (RendicionCuentasBean)this.getCurrentRowObject();
//		
//		StringBuffer link = new StringBuffer();
// 		
//		link.append("<span id=\"montoContraParteEvaluacion");
//		link.append(bean.getId());
//		link.append("\">");
//		link.append(twoPlaces.format(bean.getMontoContraparteEvaluacion()));
//		link.append("</span>");
//
//		return link.toString();
//	}
//
//	public String getObservaciones() {
//		RendicionCuentasBean bean= (RendicionCuentasBean)this.getCurrentRowObject();
//		
//		StringBuffer link = new StringBuffer();
//		
//		link.append("<span id=\"observaciones");
//		link.append(bean.getId());
//		link.append("\">");
//		link.append(bean.getObservaciones()==null ? "" : bean.getObservaciones());
//		link.append("</span>");
//
//		return link.toString();		
//	}	
}


