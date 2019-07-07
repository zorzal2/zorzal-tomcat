package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.flujo;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo.FlujoBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.flujo.FlujoDTO;

public class FlujoDTOAssembler {
	public static FlujoDTO buildDto(FlujoBean bean) {
		if(bean==null) return null;
		FlujoDTO dto = new FlujoDTO();
		dto.setId(bean.getId());
		dto.setParte(bean.getParte());
		dto.setContraparte(bean.getContraparte());
		dto.setTotal(bean.getTotal());
		dto.setPeriodo(bean.getPeriodo());
		return dto;
	}
	
	public static FlujoBean updateBean(FlujoDTO dto) {
		if(dto==null) return null;
		FlujoBean bean = new FlujoBean();
		bean.setId(dto.getId());
		bean.setParte(dto.getParte());
		bean.setContraparte(dto.getContraparte());
		bean.setTotal(dto.getTotal());
		bean.setPeriodo(dto.getPeriodo());
		return bean;
	}
}
