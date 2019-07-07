package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.CiiuBean;
import com.fontar.data.impl.domain.dto.CiiuDTO;
import com.pragma.data.assembler.GenericAssembler;

public class CiiuAssembler extends GenericAssembler<CiiuBean, CiiuDTO> {

	private static class SingletonHolder {
		private static CiiuAssembler instance = new CiiuAssembler();
	}

	public static CiiuAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(CiiuBean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("codigo", bean.getCodigo());
		map.put("nombre", bean.getNombre());
		map.put("idPadre", bean.getIdPadre());

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(CiiuDTO dto) {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("codigo", dto.getCodigo());
		map.put("nombre", dto.getNombre());
		map.put("idPadre", dto.getIdPadre());

		return map;
	}

	@Override
	protected CiiuBean newBeanInstance() {
		return new CiiuBean();
	}

	@Override
	protected CiiuDTO newDtoInstance() {
		return new CiiuDTO();
	}

}
