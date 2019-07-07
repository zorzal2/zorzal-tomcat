package com.fontar.web.decorator.seguimientos.seguimientos.anr;

import java.math.BigDecimal;

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
		return "RendicionCuentasANREditar.do";
	}

	protected String borrarAction() {
		return "RendicionCuentasANRBorrar.do";
	}
	
	public EditableMoney getMontoParteAprobadoOGestionado() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
			
		//return idProyectoOrigen != null ? (this.getCodigoProyecto( idProyectoOrigen) + " " + editarMonto.displayValue()): "";
		
		if(bean.getMontoParteGestion()!=null){ 
			EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoGestion",bean.getId() ,"RendicionCuentas", "montoParteGestion", bean.getMontoParteAprobadoOGestionado());
			return (new EditableMoney(bean.getMontoParteAprobadoOGestionado(), editarMonto));
			}
		else {
			if(bean.getMontoParteEvaluacion()!=null){
				EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAprobado",bean.getId() ,"RendicionCuentas", "montoParteEvaluacion", bean.getMontoParteAprobadoOGestionado());	
				return (new EditableMoney(bean.getMontoParteAprobadoOGestionado(), editarMonto));
			}
			else {
				return null;
			}
		}
	}
	
	public BigDecimal getMontoTotalAprobadoOGestionado() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		return bean.getMontoTotalAprobadoOGestionado();
	}
	
	public EditableMoney getMontoContraparteAprobadoOGestionado() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		if(bean.getMontoContraparteGestion()!=null){ 
			EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoGestion",bean.getId() ,"RendicionCuentas", "montoContraparteGestion", bean.getMontoContraparteAprobadoOGestionado());
			return (new EditableMoney(bean.getMontoContraparteAprobadoOGestionado(), editarMonto));
			}
		else { 
			if(bean.getMontoContraparteEvaluacion()!=null){
				EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAprobado",bean.getId() ,"RendicionCuentas", "montoContraparteEvaluacion", bean.getMontoContraparteAprobadoOGestionado());	
				return (new EditableMoney(bean.getMontoContraparteAprobadoOGestionado(), editarMonto));
			}
			else {
				return null;
			}
		}	
	}
	
	public EditableMoney getMontoParteRendicion(){
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoSolicitado",bean.getId() ,"RendicionCuentas", "montoParteRendicion", bean.getMontoParteRendicion(), "administracionSeguimientoService");
		return (new EditableMoney(bean.getMontoParteRendicion(), editarMonto));
	}

	public EditableMoney getMontoContraparteRendicion(){
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoSolicitado",bean.getId() ,"RendicionCuentas", "montoContraparteRendicion", bean.getMontoContraparteRendicion(),"administracionSeguimientoService");
		return (new EditableMoney(bean.getMontoContraparteRendicion(), editarMonto));
	}
	
	
	public EditableMoney getMontoContraparteGestionado(){
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		if(bean.getMontoContraparteGestion()!=null){ 
			EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoGestion",bean.getId() ,"RendicionCuentas", "montoContraparteGestion", bean.getMontoContraparteGestion());
			return (new EditableMoney(bean.getMontoContraparteGestion(), editarMonto));
		}else
			return null;
	}
	
	public EditableMoney getMontoContraparteAprobado(){
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		if(bean.getMontoContraparteEvaluacion()!=null){ 
			EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAprobado",bean.getId() ,"RendicionCuentas", "montoContraparteEvaluacion", bean.getMontoContraparteEvaluacion());	
			return (new EditableMoney(bean.getMontoContraparteEvaluacion(), editarMonto));
		}else
			return null;
	}
	

	public EditableMoney getMontoParteGestionado(){
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		if(bean.getMontoParteGestion()!=null){ 
			EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoGestion",bean.getId() ,"RendicionCuentas", "montoParteGestion", bean.getMontoParteGestion());
			return (new EditableMoney(bean.getMontoParteGestion(), editarMonto));
		}else
			return null;
	}
	
	public EditableMoney getMontoParteAprobado(){
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		if(bean.getMontoParteEvaluacion()!=null){ 
			EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAprobado",bean.getId() ,"RendicionCuentas", "montoParteEvaluacion", bean.getMontoParteEvaluacion());	
			return (new EditableMoney(bean.getMontoParteEvaluacion(), editarMonto));
		}else
			return null;
	}
	
/* NO VA!
 	public EditableMoney getMontoTotalGestionado(){
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		if(bean.getMontoTotalGestion()!=null){ 
			EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoGestion",bean.getId() ,"RendicionCuentas", "montoTotalGestion", bean.getMontoTotalGestion());
			return (new EditableMoney(bean.getMontoTotalGestion(), editarMonto));
		}else
			return null;
	}
	
	public EditableMoney getMontoTotalAprobado(){
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		if(bean.getMontoTotalEvaluacion()!=null){ 
			EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAprobado",bean.getId() ,"RendicionCuentas", "montoTotalEvaluacion", bean.getMontoTotalEvaluacion());	
			return (new EditableMoney(bean.getMontoTotalEvaluacion(), editarMonto));
		}else
			return null;
	}
*/
	
}


