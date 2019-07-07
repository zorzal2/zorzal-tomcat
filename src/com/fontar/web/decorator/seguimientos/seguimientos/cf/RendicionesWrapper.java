package com.fontar.web.decorator.seguimientos.seguimientos.cf;


import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.web.decorator.Util.EditableMoney;
import com.fontar.web.decorator.link.impl.EditarMonto;


/**
 * 
 * @author llobeto
 *  
 */
public class RendicionesWrapper extends com.fontar.web.decorator.seguimientos.seguimientos.RendicionesWrapper {

	protected String editarAction() {
		return "RendicionCuentasCFEditar.do";
	}

	protected String borrarAction() {
		return "RendicionCuentasCFBorrar.do";
	}
	
	public EditableMoney getMontoTotalAprobadoOGestionado() {
		
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		if(bean.getMontoParteGestion()!=null && bean.getMontoContraparteGestion()!=null){
				return this.getMontoTotalGestionado();
			}
		else { 
			if(bean.getMontoParteEvaluacion()!=null && bean.getMontoContraparteEvaluacion()!=null){ 
				return this.getMontoTotalAprobado();
			}
			else {
				return null;
			}
		}
	}
	
	public EditableMoney getMontoTotal() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.editarMontoSolicitado",bean.getId() ,"RendicionCuentas", "montoContraparteRendicion", bean.getMontoContraparteRendicion(), "administracionSeguimientoService");
		return (new EditableMoney(bean.getMontoContraparteRendicion(), editarMonto));
	}
	
	public EditableMoney getMontoTotalAprobado() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		if(bean.getMontoParteEvaluacion()!=null && bean.getMontoContraparteEvaluacion()!=null){
			EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAprobado",bean.getId() ,"RendicionCuentas", "montoContraparteEvaluacion", bean.getMontoTotalEvaluacion());	
			return (new EditableMoney(bean.getMontoTotalEvaluacion() , editarMonto));
		}else
			return null;
	}
	
	public EditableMoney getMontoTotalGestionado() {		
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		if(bean.getMontoParteGestion()!=null && bean.getMontoContraparteGestion()!=null){
			EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoGestion",bean.getId() ,"RendicionCuentas", "montoContraparteGestion", bean.getMontoTotalGestion());
			return (new EditableMoney(bean.getMontoTotalGestion(), editarMonto));
		}else
			return null;
	}
	
}

