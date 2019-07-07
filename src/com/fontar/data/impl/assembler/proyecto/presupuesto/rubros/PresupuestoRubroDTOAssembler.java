package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.PresupuestoRubroDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.RubroDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;


public abstract class PresupuestoRubroDTOAssembler implements Assembler {
	public DTO buildDto(Bean bean) {
		PresupuestoRubroBean presupuestoRubroBean = (PresupuestoRubroBean)bean;
		if(bean==null) return null;
		PresupuestoRubroDTO dto = newDTO();
		dto.setId(bean.getId());
		dto.setRubro((RubroDTO) RubroDTOAssembler.instance.buildDto(presupuestoRubroBean.getRubro()));
		dto.setMontoParte(presupuestoRubroBean.getMontoParte());
		dto.setMontoContraparte(presupuestoRubroBean.getMontoContraparte());
		return dto;
	}
	
	public Bean updateBean(DTO dto) {
		PresupuestoRubroDTO presupuestoRubroDTO = (PresupuestoRubroDTO) dto; 
		if(dto==null) return null;
		PresupuestoRubroBean bean = newBean();
		bean.setId(presupuestoRubroDTO.getId());
		bean.setRubro((RubroBean) RubroDTOAssembler.instance.updateBean(presupuestoRubroDTO.getRubro()));
		bean.setMontoParte(presupuestoRubroDTO.getMontoParte());
		bean.setMontoContraparte(presupuestoRubroDTO.getMontoContraparte());
		return bean;
	}
	
	public abstract PresupuestoRubroBean newBean();
	public abstract PresupuestoRubroDTO newDTO();
}