package com.fontar.proyecto.presupuesto.xml.reader.readers;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemConsejeroTecnologicoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoBean;
import com.fontar.proyecto.presupuesto.xml.Names;

public class ItemConsejeroTecnologicoReader extends ItemRecursoHumanoReader {

	@Override
	protected Collection<String> attributesToRead() {
		Collection<String> collection = super.attributesToRead();
		collection.add(Names.categoria);
		collection.add(Names.titulo);
		return collection;
	}

	@Override
	public PresupuestoItemRecursoHumanoBean emptyInstance() {
		return new PresupuestoItemConsejeroTecnologicoBean();
	}
}
