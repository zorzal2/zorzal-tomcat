package com.fontar.proyecto.presupuesto.xml.reader.readers;

import java.util.Collection;
import java.util.Map;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemInsumoBean;
import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.util.CollectionUtils;

public class ItemInsumoReader extends ItemReader {

	@Override
	protected Collection<String> attributesToRead() {
		return CollectionUtils.add(
				Names.nombre,
				Names.cantidad,
				Names.contraparte,
				Names.costoUnitario,
				Names.parte,
				Names.total).to(super.attributesToRead());
	}

	@Override
	protected PresupuestoItemBean emptyInstance() {
		return new PresupuestoItemInsumoBean();
	}

	@Override
	protected Map<String, String> attribute2PropertyMap() {
		Map<String, String> map = super.attribute2PropertyMap();
		map.put(Names.unidadDeMedida, "unidadMedida");
		return map;
	}
}
