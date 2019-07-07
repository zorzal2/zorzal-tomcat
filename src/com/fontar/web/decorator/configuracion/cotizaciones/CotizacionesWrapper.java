package com.fontar.web.decorator.configuracion.cotizaciones;

import com.fontar.data.impl.domain.bean.CotizacionBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.BorrarLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.pragma.util.StringUtil;

public class CotizacionesWrapper extends TableDecoratorSupport {

	public String getLinkEliminar() {
		CotizacionBean cotizacion = (CotizacionBean) this.getCurrentRowObject();
		BorrarLink borrarLink = new BorrarLink("CotizacionEliminar.do","app.alt.cotizacion.eliminar",cotizacion.getId());
		borrarLink.setSimplePermissionsRequired("COTIZACIONES-ELIMINAR");  
		return borrarLink.displayValue();
	}

	public String getLinkEditar() {
		CotizacionBean cotizacion = (CotizacionBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("CotizacionEditar.do","app.alt.cotizacion.editar",cotizacion.getId());
		editarLink.setSimplePermissionsRequired("COTIZACIONES-EDITAR");  
		return editarLink.displayValue();
	}
	
	public String getMonto() {
		CotizacionBean cotizacion = (CotizacionBean) this.getCurrentRowObject();
		return StringUtil.formatMoneyForPresentation(cotizacion.getMonto());
	}
	
	public String getFecha() {
		CotizacionBean cotizacion = (CotizacionBean) this.getCurrentRowObject();
		return StringUtil.formatDate(cotizacion.getFecha());
	}
}