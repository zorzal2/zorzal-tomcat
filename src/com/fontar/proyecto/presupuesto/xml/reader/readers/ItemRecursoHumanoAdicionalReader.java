package com.fontar.proyecto.presupuesto.xml.reader.readers;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoAdicionalBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoBean;

public class ItemRecursoHumanoAdicionalReader extends ItemRecursoHumanoReader {

	@Override
	public PresupuestoItemRecursoHumanoBean emptyInstance() {
		return new PresupuestoItemRecursoHumanoAdicionalBean();
	}
}
