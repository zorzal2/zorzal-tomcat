package com.fontar.proyecto.presupuesto.xml.writer.writers;

import java.util.Map;

import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.util.CollectionUtils;

public class ItemCanonInstitucionalWriter extends ItemWriter {

	@Override
	protected Map<String, String> attribute2PropertyMap() {
		return CollectionUtils.add(
				Names.empresa, "nombre",
				Names.contraparte, "contraparte",
				Names.parte, "parte",
				Names.total, "total()"
		).to(super.attribute2PropertyMap());
	}

	@Override
	protected String tagName() {
		return Names.presupuestoItemCanonInstitucional;
	}
}
