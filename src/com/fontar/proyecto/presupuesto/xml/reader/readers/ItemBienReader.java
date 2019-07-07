package com.fontar.proyecto.presupuesto.xml.reader.readers;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBienBean;
import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.util.CollectionUtils;

public class ItemBienReader extends ItemReader {

	public Collection<String> attributesToRead() {
		return CollectionUtils.listWith( 
			Names.nombre,
			Names.contraparte,
			Names.parte,
			Names.total
		);
	}

	@Override
	protected PresupuestoItemBean emptyInstance() {
		return new PresupuestoItemBienBean();
	}
}
