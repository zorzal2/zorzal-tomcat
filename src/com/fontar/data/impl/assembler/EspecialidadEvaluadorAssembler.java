package com.fontar.data.impl.assembler;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.EspecialidadEvaluadorBean;
import com.fontar.data.impl.domain.dto.EspecialidadEvaluadorDTO;
import com.pragma.data.assembler.GenericAssembler;

public class EspecialidadEvaluadorAssembler extends
		GenericAssembler<EspecialidadEvaluadorBean, EspecialidadEvaluadorDTO> {

	private static class SingletonHolder {
		private static EspecialidadEvaluadorAssembler instance = new EspecialidadEvaluadorAssembler();
	}

	public static EspecialidadEvaluadorAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(EspecialidadEvaluadorBean bean) throws ParseException {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("codigo", bean.getCodigo());
		map.put("nombre", bean.getNombre());
		map.put("activo", bean.getActivo());
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(EspecialidadEvaluadorDTO dto) throws ParseException {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("codigo", dto.getCodigo());
		map.put("nombre", dto.getNombre());
		map.put("activo", dto.getActivo());
		return map;
	}

	@Override
	protected EspecialidadEvaluadorBean newBeanInstance() {
		return new EspecialidadEvaluadorBean();
	}

	@Override
	protected EspecialidadEvaluadorDTO newDtoInstance() {
		return new EspecialidadEvaluadorDTO();
	}
}