package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;


public abstract class PresupuestoItemRecursoHumanoDTOAssembler extends PresupuestoItemDTOAssembler {

	public boolean canHandle(Bean bean) {
		return bean instanceof PresupuestoItemRecursoHumanoBean;
	}

	public boolean canHandle(DTO dto) {
		return dto instanceof PresupuestoItemRecursoHumanoDTO;
	}

	@Override
	public DTO buildDto(Bean bean) {
		PresupuestoItemRecursoHumanoBean rh = (PresupuestoItemRecursoHumanoBean)bean; 
		PresupuestoItemRecursoHumanoDTO dto = (PresupuestoItemRecursoHumanoDTO)super.buildDto(bean);
		dto.setParticipacion(rh.getParticipacion());
		dto.setProfesion(rh.getProfesion());
		dto.setFuncion(rh.getFuncion());
		dto.setIdentificacionTributaria(rh.getIdentificacionTributaria());
		dto.setCostoMensual(rh.getCostoMensual());
		dto.setDedicacion(rh.getDedicacion());
		return dto;
	}
	@Override
	public Bean updateBean(DTO dto) {
		PresupuestoItemRecursoHumanoDTO rh = (PresupuestoItemRecursoHumanoDTO)dto; 
		PresupuestoItemRecursoHumanoBean bean = (PresupuestoItemRecursoHumanoBean)super.updateBean(dto);
		bean.setParticipacion(rh.getParticipacion());
		bean.setProfesion(rh.getProfesion());
		bean.setFuncion(rh.getFuncion());
		bean.setIdentificacionTributaria(rh.getIdentificacionTributaria());
		bean.setCostoMensual(rh.getCostoMensual());
		bean.setDedicacion(rh.getDedicacion());
		return bean;
	}
}
