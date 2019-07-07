package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoPropioBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoPropioDTO;

public class PresupuestoItemRecursoHumanoPropioDTOAssembler extends
		PresupuestoItemRecursoHumanoDTOAssembler {
	
	public static final PresupuestoItemRecursoHumanoPropioDTOAssembler instance =
		new PresupuestoItemRecursoHumanoPropioDTOAssembler();

	@Override
	protected PresupuestoItemDTO newDTO() {
		return new PresupuestoItemRecursoHumanoPropioDTO();
	}

	@Override
	protected PresupuestoItemBean newBean() {
		return new PresupuestoItemRecursoHumanoPropioBean();
	}

}
