package com.fontar.proyecto.presupuesto.xml.reader.readers;

import java.util.Collection;
import java.util.Map;

import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.util.CollectionUtils;

public abstract class ItemRecursoHumanoReader extends ItemReader {

	@Override
	protected Map<String, String> attribute2PropertyMap() {
		return CollectionUtils.add(
				Names.idTributaria, "identificacionTributaria",
				Names.costoMensual, "costoMensual"
		).to(super.attribute2PropertyMap());
	}

	@Override
	protected Collection<String> attributesToRead() {
		return CollectionUtils.add(
				Names.funcion,
				Names.nombre,
				Names.profesion,
				Names.contraparte,
				Names.dedicacion,
				Names.parte,
				Names.participacion,
				Names.total
		).to(super.attributesToRead());
	}
}
