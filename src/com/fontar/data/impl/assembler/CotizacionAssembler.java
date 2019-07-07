package com.fontar.data.impl.assembler;

import java.util.ArrayList;
import java.util.List;

import com.fontar.data.impl.domain.bean.CotizacionBean;
import com.fontar.data.impl.domain.dto.CotizacionDTO;

public class CotizacionAssembler {

	private static class SingletonHolder {
		private static CotizacionAssembler instance = new CotizacionAssembler();
	}

	public static CotizacionAssembler getInstance() {
		return SingletonHolder.instance;

	}
	
	public CotizacionDTO buildDto(CotizacionBean bean) {
		CotizacionDTO cotizacionDTO = new CotizacionDTO();
		cotizacionDTO.setFecha(bean.getFecha());
		cotizacionDTO.setId(bean.getId());
		cotizacionDTO.setIdMoneda(bean.getIdMoneda());
		cotizacionDTO.setMoneda(MonedaAssembler.getInstance().buildDto(bean.getMoneda()));
		cotizacionDTO.setMonto(bean.getMonto());
		return cotizacionDTO;
	}

	public CotizacionBean buildBean(CotizacionDTO dto) {
		CotizacionBean bean = new CotizacionBean();
		bean.setFecha(dto.getFecha());
		bean.setId(dto.getId());
		bean.setIdMoneda(dto.getIdMoneda());
		bean.setMonto(dto.getMonto());
		return bean;
	}

	public List<CotizacionDTO> buildDto(List<CotizacionBean> beans) {
		List<CotizacionDTO> list = new ArrayList<CotizacionDTO>(beans.size());
		for(CotizacionBean bean : beans) {
			list.add(buildDto(bean));
		}
		return list;
	}
}
