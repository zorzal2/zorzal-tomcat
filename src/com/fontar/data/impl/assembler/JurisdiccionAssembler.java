package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.JurisdiccionBean;
import com.fontar.data.impl.domain.dto.JurisdiccionDTO;
import com.pragma.data.assembler.GenericAssembler;

public class JurisdiccionAssembler extends GenericAssembler<JurisdiccionBean, JurisdiccionDTO> {
	private static class SingletonHolder {
		private static JurisdiccionAssembler instance = new JurisdiccionAssembler();
	}

	public static JurisdiccionAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(JurisdiccionBean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("codigo", bean.getCodigo());
		map.put("descripcion", bean.getDescripcion());
		map.put("activo", bean.getActivo());
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(JurisdiccionDTO dto) {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("codigo", dto.getCodigo());
		map.put("descripcion", dto.getDescripcion());
		map.put("activo", dto.getActivo());
		return map;
	}

	@Override
	protected JurisdiccionBean newBeanInstance() {
		return new JurisdiccionBean();
	}

	@Override
	protected JurisdiccionDTO newDtoInstance() {
		return new JurisdiccionDTO();
	}

}
