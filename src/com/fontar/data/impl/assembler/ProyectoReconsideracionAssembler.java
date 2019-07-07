package com.fontar.data.impl.assembler;

import com.fontar.data.impl.domain.bean.ProyectoReconsideracionBean;
import com.fontar.data.impl.domain.dto.ProyectoReconsideracionDTO;

public class ProyectoReconsideracionAssembler {

	private static class SingletonHolder {
		private static ProyectoReconsideracionAssembler instance = new ProyectoReconsideracionAssembler();
	}

	public static ProyectoReconsideracionAssembler getInstance() {
		return SingletonHolder.instance;

	}

	public ProyectoReconsideracionDTO buildDto(ProyectoReconsideracionBean bean) {

		ProyectoReconsideracionDTO dto = new ProyectoReconsideracionDTO();
		dto.setId(bean.getId());
		dto.setDictamen(bean.getDictamen());
		dto.setFecha(bean.getFecha());
		dto.setFundamentacion(bean.getFundamentacion());
		dto.setObservacion(bean.getObservacion());
		dto.setResolucion(bean.getResolucion());
		dto.setResultado(bean.getResultado());
		
		return dto;
	}
}
