package com.fontar.data.impl.assembler;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.FuenteFinanciamientoBean;
import com.fontar.data.impl.domain.dto.FuenteFinanciamientoDTO;
import com.pragma.data.assembler.GenericAssembler;

public class FuenteFinanciamientoAssembler extends GenericAssembler<FuenteFinanciamientoBean, FuenteFinanciamientoDTO> {

	private static class SingletonHolder {
		private static FuenteFinanciamientoAssembler instance = new FuenteFinanciamientoAssembler();
	}

	public static FuenteFinanciamientoAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(FuenteFinanciamientoBean bean) throws ParseException {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("identificador", bean.getIdentificador());
		map.put("denominacion", bean.getDenominacion());
		map.put("activo", bean.getActivo());
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(FuenteFinanciamientoDTO dto) throws ParseException {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("identificador", dto.getIdentificador());
		map.put("denominacion", dto.getDenominacion());
		map.put("activo", dto.getActivo());
		return map;
	}

	@Override
	protected FuenteFinanciamientoBean newBeanInstance() {
		return new FuenteFinanciamientoBean();
	}

	@Override
	protected FuenteFinanciamientoDTO newDtoInstance() {
		return new FuenteFinanciamientoDTO();
	}
}