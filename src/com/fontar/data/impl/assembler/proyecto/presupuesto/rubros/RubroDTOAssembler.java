package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.RubroDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;



public class RubroDTOAssembler implements Assembler {

	public static final RubroDTOAssembler instance = new RubroDTOAssembler(); 

	public RubroDTO buildDto(Bean bean) {
		RubroBean rubroBean = (RubroBean)bean;
		if(rubroBean==null) return null;
		RubroDTO dto = new RubroDTO();
		dto.setId(bean.getId());
		dto.setNombre(rubroBean.getNombre());
		dto.setCodigo(rubroBean.getCodigo());
		dto.setCodigoLargo(rubroBean.getCodigoLargo());
		dto.setRubroPadre((RubroDTO)buildDto(rubroBean.getRubroPadre()));
		dto.setTipo(rubroBean.getTipo());
		dto.setTipoRendicion(rubroBean.getTipoRendicion());
		dto.setNroOrden(rubroBean.getNroOrden());
		return dto;
	}

	public Bean updateBean(DTO dto) {
		if(dto==null) return null;
		RubroDTO rubroDTO = (RubroDTO) dto;
		RubroBean bean = new RubroBean();
		bean.setId(rubroDTO.getId());
		bean.setNombre(rubroDTO.getNombre());
		bean.setCodigo(rubroDTO.getCodigo());
		bean.setCodigoLargo(rubroDTO.getCodigoLargo());
		bean.setRubroPadre((RubroBean)updateBean(rubroDTO.getRubroPadre()));
		bean.setTipo(rubroDTO.getTipo());
		bean.setTipoRendicion(rubroDTO.getTipoRendicion());
		bean.setNroOrden(rubroDTO.getNroOrden());
		return bean;
	}

	public boolean canHandle(Bean bean) {
		return bean instanceof RubroBean;
	}

	public boolean canHandle(DTO dto) {
		return dto instanceof RubroDTO;
	}
}
