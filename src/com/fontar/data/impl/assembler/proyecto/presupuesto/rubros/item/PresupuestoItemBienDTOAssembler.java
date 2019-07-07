package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBienBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemBienDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;


public class PresupuestoItemBienDTOAssembler extends PresupuestoItemDTOAssembler {

	public static final PresupuestoItemBienDTOAssembler instance = new PresupuestoItemBienDTOAssembler();
	
	public boolean canHandle(Bean bean) {
		return bean instanceof PresupuestoItemBienBean;
	}

	public boolean canHandle(DTO dto) {
		return dto instanceof PresupuestoItemBienDTO;
	}

	@Override
	protected PresupuestoItemDTO newDTO() {
		return new PresupuestoItemBienDTO();
	}

	@Override
	protected PresupuestoItemBean newBean() {
		return new PresupuestoItemBienBean();
	}

}
