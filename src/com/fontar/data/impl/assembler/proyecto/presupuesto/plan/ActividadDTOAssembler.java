package com.fontar.data.impl.assembler.proyecto.presupuesto.plan;

import com.fontar.data.api.dao.proyecto.presupuesto.plan.EtapaDAO;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.ActividadBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.plan.ActividadDTO;
import com.pragma.util.ContextUtil;

public class ActividadDTOAssembler {
	public static ActividadDTO buildDto(ActividadBean bean) {
		if(bean==null) return null;
		ActividadDTO dto = new ActividadDTO();
		dto.setId(bean.getId());
		dto.setEtapaId(bean.getEtapa().getId());
		dto.setNombre(bean.getNombre());
		dto.setDescripcion(bean.getDescripcion());
		dto.setInicio(bean.getInicio());
		dto.setFin(bean.getFin());
		dto.setAvance(bean.getAvance());
		dto.setObservacion(bean.getObservacion());
		return dto;
	}
	
	public static ActividadBean updateBean(ActividadDTO dto) {
		if(dto==null) return null;
		ActividadBean bean = new ActividadBean();
		bean.setId(dto.getId());
		Long etapaId = dto.getEtapaId();
		if(etapaId==null) {
			bean.setEtapa(null);
		} else {
			EtapaDAO etapaDAO = (EtapaDAO)ContextUtil.getBean("etapaDao");
			bean.setEtapa(etapaDAO.read(etapaId));
		}
		bean.setNombre(dto.getNombre());
		bean.setDescripcion(dto.getDescripcion());
		bean.setInicio(dto.getInicio());
		bean.setFin(dto.getFin());
		bean.setAvance(dto.getAvance());
		bean.setObservacion(dto.getObservacion());
		return bean;
	}
}
