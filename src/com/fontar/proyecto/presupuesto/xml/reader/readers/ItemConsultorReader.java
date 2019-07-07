package com.fontar.proyecto.presupuesto.xml.reader.readers;

import java.util.Collection;
import java.util.Map;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemConsultorBean;
import com.fontar.proyecto.presupuesto.xml.Names;

public class ItemConsultorReader extends ItemReader {

	@Override
	protected Map<String, String> attribute2PropertyMap() {
		Map<String, String> map = super.attribute2PropertyMap();
		map.put(Names.costoMensual, "costoMensual");
		return map;		
	}

	@Override
	protected Collection<String> attributesToRead() {
		Collection<String> collection = super.attributesToRead();
		collection.add(Names.nombre);
		collection.add(Names.contraparte);
		collection.add(Names.parte);
		collection.add(Names.total);
		collection.add(Names.participacion);
		return collection;
	}

	@Override
	protected PresupuestoItemBean emptyInstance() {
		return new PresupuestoItemConsultorBean();
	}
}
