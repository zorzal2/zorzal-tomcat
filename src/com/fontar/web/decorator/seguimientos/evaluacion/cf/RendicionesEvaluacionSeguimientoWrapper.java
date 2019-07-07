package com.fontar.web.decorator.seguimientos.evaluacion.cf;

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
public class RendicionesEvaluacionSeguimientoWrapper extends TableDecoratorSupport {
	
	
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
	
}


