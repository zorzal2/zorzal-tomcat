package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemConsultorBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemConsultorDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;


public class PresupuestoItemConsultorDTOAssembler extends PresupuestoItemDTOAssembler {
	public static final PresupuestoItemConsultorDTOAssembler instance = new PresupuestoItemConsultorDTOAssembler(); 
	@Override
	public DTO buildDto(Bean bean) {
		PresupuestoItemConsultorDTO dto = (PresupuestoItemConsultorDTO)super.buildDto(bean);
		dto.setCostoMensual(((PresupuestoItemConsultorBean)bean).getCostoMensual());
		dto.setParticipacion(((PresupuestoItemConsultorBean)bean).getParticipacion());
		return dto;
	}
	public boolean canHandle(Bean bean) {
		return bean instanceof PresupuestoItemConsultorBean;
	}
	public boolean canHandle(DTO dto) {
		return dto instanceof PresupuestoItemConsultorDTO;
	}
	@Override
	public Bean updateBean(DTO dto) {
		PresupuestoItemConsultorBean bean = (PresupuestoItemConsultorBean)super.updateBean(dto);
		bean.setCostoMensual(((PresupuestoItemConsultorDTO)dto).getCostoMensual());
		bean.setParticipacion(((PresupuestoItemConsultorDTO)dto).getParticipacion());
		return bean;
	}
	@Override
	protected PresupuestoItemDTO newDTO() {
		return new PresupuestoItemConsultorDTO();
	}
	@Override
	protected PresupuestoItemBean newBean() {
		return new PresupuestoItemConsultorBean();
	}
}