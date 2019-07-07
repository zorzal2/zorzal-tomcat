package com.fontar.proyecto.presupuesto.xml.writer.writers;

import java.util.Collection;
import java.util.Map;

import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.util.CollectionUtils;

public class ItemInsumoWriter extends ItemWriter {

	@Override
	protected Map<String, String> attribute2PropertyMap() {
		return CollectionUtils.add(
				Names.unidadDeMedida, "unidadMedida"
		).to(super.attribute2PropertyMap());
	}

	@Override
	protected Collection<String> attributesToWrite() {
		return CollectionUtils.add(
				Names.nombre,
				Names.cantidad,
				Names.contraparte,
				Names.costoUnitario,
				Names.parte,
				Names.total
		).to(super.attributesToWrite());
	}

	@Override
	protected String tagName() {
		return Names.presupuestoItemInsumo;
	}

}
