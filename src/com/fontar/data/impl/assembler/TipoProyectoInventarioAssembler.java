package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.TipoProyectoBean;
import com.fontar.data.impl.domain.dto.TipoProyectoInventarioDTO;
import com.pragma.data.assembler.GenericAssembler;

public class TipoProyectoInventarioAssembler extends GenericAssembler<TipoProyectoBean, TipoProyectoInventarioDTO> {

	private static class SingletonHolder {
		private static TipoProyectoInventarioAssembler instance = new TipoProyectoInventarioAssembler();
	}

	public static TipoProyectoInventarioAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(TipoProyectoBean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("nombre", bean.getNombre());
		map.put("nombreCorto", bean.getNombreCorto());
		map.put("activo", bean.getActivo());
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(TipoProyectoInventarioDTO dto) {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("nombre", dto.getNombre());
		map.put("nombreCorto", dto.getNombreCorto());
		map.put("activo", dto.getActivo());
		return map;
	}

	@Override
	protected TipoProyectoBean newBeanInstance() {
		return new TipoProyectoBean();
	}

	@Override
	protected TipoProyectoInventarioDTO newDtoInstance() {
		return new TipoProyectoInventarioDTO();
	}

}
