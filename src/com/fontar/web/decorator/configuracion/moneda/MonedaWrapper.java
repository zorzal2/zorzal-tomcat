package com.fontar.web.decorator.configuracion.moneda;

import com.fontar.data.impl.domain.dto.MonedaDTO;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.EditarLink;

public class MonedaWrapper extends TableDecoratorSupport {

	public String getLinkEditar() {
		MonedaDTO moneda = (MonedaDTO) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("MonedaEditar.do","app.alt.moneda.editar",moneda.getId());
		editarLink.setSimplePermissionsRequired("MONEDAS-EDITAR");  
		return editarLink.displayValue();
	}
}