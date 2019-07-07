package com.fontar.proyecto.presupuesto.xml.writer.writers;

import java.util.Collection;
import java.util.Map;

import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.util.CollectionUtils;

public abstract class ItemRecursoHumanoWriter extends ItemWriter {
	@Override
	protected Map<String, String> attribute2PropertyMap() {
		return CollectionUtils.add(
			Names.costoMensual, "costoMensual",
			Names.idTributaria, "identificacionTributaria"
		).to(super.attribute2PropertyMap());
	}

	@Override
	protected Collection<String> attributesToWrite() {
		return CollectionUtils.add(
				Names.funcion,
				Names.nombre,
				Names.profesion,
				Names.contraparte,
				Names.dedicacion,
				Names.parte,
				Names.participacion,
				Names.total		
		).to(super.attributesToWrite());
	}
}
