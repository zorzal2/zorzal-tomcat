package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemCanonInstitucionalBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemCanonInstitucionalDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;


public class PresupuestoItemCanonInstitucionalDTOAssembler extends PresupuestoItemDTOAssembler {
	public static final PresupuestoItemCanonInstitucionalDTOAssembler instance = new PresupuestoItemCanonInstitucionalDTOAssembler(); 
	public boolean canHandle(Bean bean) {
		return bean instanceof PresupuestoItemCanonInstitucionalBean;
	}
	public boolean canHandle(DTO dto) {
		return dto instanceof PresupuestoItemCanonInstitucionalDTO;
	}
	@Override
	protected PresupuestoItemDTO newDTO() {
		return new PresupuestoItemCanonInstitucionalDTO();
	}
	@Override
	protected PresupuestoItemBean newBean() {
		return new PresupuestoItemCanonInstitucionalBean();
	}
}