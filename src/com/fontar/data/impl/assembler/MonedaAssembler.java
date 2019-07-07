package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.MonedaBean;
import com.fontar.data.impl.domain.dto.MonedaDTO;
import com.pragma.data.assembler.GenericAssembler;

public class MonedaAssembler extends GenericAssembler<MonedaBean, MonedaDTO> {

	private static class SingletonHolder {
		private static MonedaAssembler instance = new MonedaAssembler();
	}

	public static MonedaAssembler getInstance() {
		return SingletonHolder.instance;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(MonedaBean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("tipoMoneda", bean.getTipoMoneda());
		map.put("descripcion", bean.getDescripcion());
		map.put("codigoEmerix", bean.getCodigoEmerix());
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(MonedaDTO dto) {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("tipoMoneda", dto.getTipoMoneda());
		map.put("descripcion", dto.getDescripcion());
		map.put("codigoEmerix", dto.getCodigoEmerix());

		return map;
	}

	@Override
	protected MonedaBean newBeanInstance() {
		return new MonedaBean();
	}

	@Override
	protected MonedaDTO newDtoInstance() {
		return new MonedaDTO();
	}

}
