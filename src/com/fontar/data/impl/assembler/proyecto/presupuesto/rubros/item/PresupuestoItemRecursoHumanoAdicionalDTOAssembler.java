package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoAdicionalBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoAdicionalDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;

public class PresupuestoItemRecursoHumanoAdicionalDTOAssembler extends PresupuestoItemRecursoHumanoDTOAssembler {

	public static final PresupuestoItemRecursoHumanoAdicionalDTOAssembler instance =
		new PresupuestoItemRecursoHumanoAdicionalDTOAssembler();
	
	@Override
	protected PresupuestoItemDTO newDTO() {
		return new PresupuestoItemRecursoHumanoAdicionalDTO();
	}

	@Override
	protected PresupuestoItemBean newBean() {
		return new PresupuestoItemRecursoHumanoAdicionalBean();
	}

	public boolean canHandle(Bean bean) {
		return bean instanceof PresupuestoItemRecursoHumanoAdicionalBean;
	}

	public boolean canHandle(DTO dto) {
		return dto instanceof PresupuestoItemRecursoHumanoAdicionalDTO;
	}

}
