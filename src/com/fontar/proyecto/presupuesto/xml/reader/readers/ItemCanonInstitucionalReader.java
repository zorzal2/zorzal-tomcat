package com.fontar.proyecto.presupuesto.xml.reader.readers;

import java.util.Map;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemCanonInstitucionalBean;
import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.util.CollectionUtils;

public class ItemCanonInstitucionalReader extends ItemReader {
	@Override
	protected Map<String, String> attribute2PropertyMap() {
		return CollectionUtils.mapWith(
				Names.empresa, "nombre",
				Names.contraparte, "contraparte",
				Names.parte, "parte",
				Names.total, "total"
		);
	}

	@Override
	protected PresupuestoItemBean emptyInstance() {
		return new PresupuestoItemCanonInstitucionalBean();
	}
}
