package com.fontar.proyecto.presupuesto.xml.writer.writers;

import java.util.Collection;

import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.util.CollectionUtils;

public class ItemBienWriter extends ItemWriter {

	@Override
	protected Collection<String> attributesToWrite() {
		return CollectionUtils.add(
				Names.nombre,
				Names.contraparte,
				Names.parte,
				Names.total
		).to(super.attributesToWrite());
	}

	@Override
	protected String tagName() {
		return Names.presupuestoItemBien;
	}

}
