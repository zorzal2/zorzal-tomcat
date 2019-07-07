package com.fontar.data.impl.assembler;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.ComisionBean;
import com.fontar.data.impl.domain.dto.ComisionDTO;
import com.pragma.data.assembler.GenericAssembler;
import com.pragma.util.DateTimeUtil;

/**
 * 
 * @author ssanchez
 * 
 */
public class ComisionAssembler extends GenericAssembler<ComisionBean, ComisionDTO> {

	private static class SingletonHolder {
		private static ComisionAssembler instance = new ComisionAssembler();
	}

	public static ComisionAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(ComisionBean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("denominacion", bean.getDenominacion());
		map.put("resolucion", bean.getResolucion());
		map.put("fechaBaja", DateTimeUtil.formatDate(bean.getFechaBaja()));
		map.put("fechaAlta", DateTimeUtil.formatDate(bean.getFechaAlta()));
		map.put("observacion", bean.getObservacion());
		map.put("descripcion", bean.getDescripcion());
		map.put("activo", bean.getActivo());

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(ComisionDTO dto) throws ParseException {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("denominacion", dto.getDenominacion());
		map.put("resolucion", dto.getResolucion());
		if(dto.getFechaBaja() != null)
			map.put("fechaBaja", dto.getFechaBaja());
		map.put("fechaAlta", dto.getFechaAlta());
		map.put("observacion", dto.getObservacion());
		map.put("descripcion", dto.getDescripcion());
		map.put("activo", dto.getActivo());

		return map;
	}

	@Override
	protected ComisionBean newBeanInstance() {
		return new ComisionBean();
	}

	@Override
	protected ComisionDTO newDtoInstance() {
		return new ComisionDTO();
	}
}
