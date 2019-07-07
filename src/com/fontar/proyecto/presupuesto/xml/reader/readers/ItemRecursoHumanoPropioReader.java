package com.fontar.proyecto.presupuesto.xml.reader.readers;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoPropioBean;

public class ItemRecursoHumanoPropioReader extends ItemRecursoHumanoReader {

	@Override
	public PresupuestoItemRecursoHumanoBean emptyInstance() {
		return new PresupuestoItemRecursoHumanoPropioBean();
	}

}
