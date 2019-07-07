package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item;

import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.Assembler;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;

public abstract class PresupuestoItemDTOAssembler implements Assembler {

	public DTO buildDto(Bean bean) {
		if(bean==null) return null;
		PresupuestoItemBean presupuestoItemBean = (PresupuestoItemBean)bean;
		PresupuestoItemDTO dto = newDTO();
		dto.setId(presupuestoItemBean.getId());
		dto.setNombre(presupuestoItemBean.getNombre());
		dto.setParte(presupuestoItemBean.getParte());
		dto.setContraparte(presupuestoItemBean.getContraparte());
		dto.setTotal(presupuestoItemBean.getTotal());
		return dto;
	}
	
	public Bean updateBean(DTO dto) {
		if(dto==null) return null;
		PresupuestoItemDTO presupuestoItemDTO = (PresupuestoItemDTO) dto;
		PresupuestoItemBean bean = newBean();
		bean.setId(presupuestoItemDTO.getId());
		bean.setNombre(presupuestoItemDTO.getNombre());
		bean.setParte(presupuestoItemDTO.getParte());
		bean.setContraparte(presupuestoItemDTO.getContraparte());
		bean.setTotal(presupuestoItemDTO.getTotal());
		return bean;
	}

	protected abstract PresupuestoItemDTO newDTO();
	protected abstract PresupuestoItemBean newBean();
}
