package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.TipoAdquisicionBean;
import com.fontar.data.impl.domain.dto.TipoAdquisicionDTO;
import com.pragma.data.assembler.GenericAssembler;

public class TipoAdquisicionAssembler extends GenericAssembler<TipoAdquisicionBean, TipoAdquisicionDTO> {

	private static class SingletonHolder {
		private static TipoAdquisicionAssembler instance = new TipoAdquisicionAssembler();
	}

	public static TipoAdquisicionAssembler getInstance() {
		return SingletonHolder.instance;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(TipoAdquisicionBean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("codigo", bean.getCodigo());
		map.put("descripcion", bean.getDescripcion());
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(TipoAdquisicionDTO dto) {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("codigo", dto.getCodigo());
		map.put("descripcion", dto.getDescripcion());

		return map;
	}

	@Override
	protected TipoAdquisicionBean newBeanInstance() {
		return new TipoAdquisicionBean();
	}

	@Override
	protected TipoAdquisicionDTO newDtoInstance() {
		return new TipoAdquisicionDTO();
	}

}
