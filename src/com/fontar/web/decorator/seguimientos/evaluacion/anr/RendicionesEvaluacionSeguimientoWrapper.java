package com.fontar.web.decorator.seguimientos.evaluacion.anr;

import static com.fontar.data.Constant.ACTION_AUTHORIZE;

import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.dto.RendicionCuentasDTO;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.Util.EditableMoney;
import com.fontar.web.decorator.link.impl.EditarJavaScriptLink;
import com.fontar.web.decorator.link.impl.EditarMonto;

/**
 * @author dberkovics
 */
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
	
	public EditableMoney getMontoParteEvaluacion(){
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAprobado",bean.getId() ,"RendicionCuentas", "montoParteEvaluacion", bean.getMontoParteEvaluacion());
		return (new EditableMoney(bean.getMontoParteEvaluacion(), editarMonto));
		
	}
	
	public EditableMoney getMontoContraparteEvaluacion(){
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAprobado",bean.getId() ,"RendicionCuentas", "montoContraparteEvaluacion", bean.getMontoContraparteEvaluacion());
		return (new EditableMoney(bean.getMontoContraparteEvaluacion(), editarMonto));
	}
		
}


