package com.fontar.proyecto.presupuesto.xml.writer.writers;

import java.util.Map;

import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.util.CollectionUtils;

public class ItemConsultorWriter extends ItemWriter {

	@Override
	protected Map<String, String> attribute2PropertyMap() {
		return CollectionUtils.add(
				Names.nombre, "nombre",
				Names.contraparte, "contraparte",
				Names.costoMensual, "costoMensual",
				Names.parte, "parte",
				Names.participacion, "participacion",
				Names.total, "total"
		).to(super.attribute2PropertyMap());
	}

	@Override
	protected String tagName() {
		return Names.presupuestoItemConsultor;
	}
}
