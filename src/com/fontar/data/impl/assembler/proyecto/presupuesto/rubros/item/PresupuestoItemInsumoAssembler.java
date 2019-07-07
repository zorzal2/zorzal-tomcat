package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemInsumoBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemInsumoDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;


public class PresupuestoItemInsumoAssembler extends PresupuestoItemDTOAssembler {
	public static final PresupuestoItemInsumoAssembler instance = new PresupuestoItemInsumoAssembler();
	
	public DTO buildDto(Bean bean) {
		PresupuestoItemInsumoBean insumo = (PresupuestoItemInsumoBean)bean;
		PresupuestoItemInsumoDTO dto = (PresupuestoItemInsumoDTO)super.buildDto(bean);
		dto.setUnidadMedida(insumo.getUnidadMedida());
		dto.setCantidad(insumo.getCantidad());
		dto.setCostoUnitario(insumo.getCostoUnitario());
		return dto;
	}
	
	public Bean updateBean(DTO dto) {
		PresupuestoItemInsumoDTO insumo = (PresupuestoItemInsumoDTO) dto;
		PresupuestoItemInsumoBean bean = (PresupuestoItemInsumoBean)super.updateBean(dto);
		bean.setUnidadMedida(insumo.getUnidadMedida());
		bean.setCantidad(insumo.getCantidad());
		bean.setCostoUnitario(insumo.getCostoUnitario());
		return bean;
	}

	public boolean canHandle(Bean bean) {
		return bean instanceof PresupuestoItemInsumoBean; 
	}

	public boolean canHandle(DTO dto) {
		return dto instanceof PresupuestoItemInsumoDTO;
	}

	@Override
	protected PresupuestoItemDTO newDTO() {
		return new PresupuestoItemInsumoDTO();
	}

	@Override
	protected PresupuestoItemBean newBean() {
		return new PresupuestoItemInsumoBean();
	}
}
