package com.fontar.web.decorator.seguimientos.seguimientos;

import org.displaytag.decorator.TableDecorator;

import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.dto.RendicionCuentasDTO;
import com.fontar.web.decorator.Util.EditableMoney;
import com.fontar.web.decorator.link.impl.EditarJavaScriptLink;
import com.fontar.web.decorator.link.impl.EditarMonto;

/**
 * Wrapper para el inventario de 
 * <code>Rendiciones de Cuenta de Gestión de Pago</code>.<br>
 * @author ssanchez
 */
public class RendicionesGestionPagoWrapper extends TableDecorator {
	
	/**
	 * Modifica los montos gestionados de una rendición.<br>
	 */
	public String getLinkEditarMontos() {
		RendicionCuentasDTO dto = (RendicionCuentasDTO) this.getCurrentRowObject();
		String link = "";
		
		EditarJavaScriptLink editarLink = new EditarJavaScriptLink("popUpEditarMontosGestionadosRendicion", "app.alt.editarMontos", dto.getId());
		link = editarLink.displayValue(); 
		
		return link;		
	}
	
	public EditableMoney getMontoParteEvaluacion(){
		RendicionCuentasDTO bean = (RendicionCuentasDTO) this.getCurrentRowObject();
		EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAprobado",bean.getId() ,"RendicionCuentas", "montoParteEvaluacion", bean.getMontoParteEvaluacion());
		return (new EditableMoney(bean.getMontoParteEvaluacion(), editarMonto));
		
	}
	
	public EditableMoney getMontoContraparteEvaluacion(){
		RendicionCuentasDTO bean = (RendicionCuentasDTO) this.getCurrentRowObject();
		EditarMonto editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAprobado",bean.getId() ,"RendicionCuentas", "montoContraparteEvaluacion", bean.getMontoContraparteEvaluacion());
		return (new EditableMoney(bean.getMontoContraparteEvaluacion(), editarMonto));
	}
}


