package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemDirectorExpertoBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDirectorExpertoDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;


public class PresupuestoItemDirectorExpertoDTOAssembler extends PresupuestoItemRecursoHumanoDTOAssembler {
	public static final PresupuestoItemDirectorExpertoDTOAssembler instance = new PresupuestoItemDirectorExpertoDTOAssembler(); 
	public boolean canHandle(Bean bean) {
		return bean instanceof PresupuestoItemDirectorExpertoBean;
	}
	public boolean canHandle(DTO dto) {
		return dto instanceof PresupuestoItemDirectorExpertoDTO;
	}
	@Override
	protected PresupuestoItemDTO newDTO() {
		return new PresupuestoItemDirectorExpertoDTO();
	}
	@Override
	protected PresupuestoItemBean newBean() {
		return new PresupuestoItemDirectorExpertoBean();
	}
}