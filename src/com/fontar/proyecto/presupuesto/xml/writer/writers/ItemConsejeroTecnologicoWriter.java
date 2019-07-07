package com.fontar.proyecto.presupuesto.xml.writer.writers;

import java.util.Collection;

import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.util.CollectionUtils;

public class ItemConsejeroTecnologicoWriter extends ItemRecursoHumanoWriter {
	@Override
	protected Collection<String> attributesToWrite() {
		return CollectionUtils.add(
				Names.categoria,
				Names.titulo
		).to(super.attributesToWrite());
	}

	@Override
	public String tagName() {
		return Names.presupuestoItemConsejeroTecnologico;
	}

}
