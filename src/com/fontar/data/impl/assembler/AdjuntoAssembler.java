package com.fontar.data.impl.assembler;

import com.fontar.data.impl.domain.bean.AdjuntoBean;
import com.fontar.data.impl.domain.dto.AdjuntoDTO;

public class AdjuntoAssembler {

	private static class SingletonHolder {
		private static AdjuntoAssembler instance = new AdjuntoAssembler();
	}

	public static AdjuntoAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	public AdjuntoDTO getBean2Dto(AdjuntoBean bean) {
		AdjuntoDTO adjuntoDTO = new AdjuntoDTO();
		adjuntoDTO.setId(bean.getId());
		adjuntoDTO.setCantidadLongitud(bean.getCantidadLongitud());
		adjuntoDTO.setDescripcion(bean.getDescripcion());
		adjuntoDTO.setFecha(bean.getFecha());
		adjuntoDTO.setIdAdjuntoContenido(bean.getIdAdjuntoContenido());
		adjuntoDTO.setNombre(bean.getNombre());
		adjuntoDTO.setTipoContenido(bean.getTipoContenido());

		return adjuntoDTO;
	}

}
