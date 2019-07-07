package com.fontar.proyecto.presupuesto.xml.reader.readers;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemDirectorExpertoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoBean;

public class ItemDirectorExpertoReader extends ItemRecursoHumanoReader {

	@Override
	public PresupuestoItemRecursoHumanoBean emptyInstance() {
		return new PresupuestoItemDirectorExpertoBean();
	}
}
