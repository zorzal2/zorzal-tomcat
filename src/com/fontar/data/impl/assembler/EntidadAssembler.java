package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.EntidadBean;
import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.data.impl.domain.dto.EntidadDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.pragma.data.assembler.GenericAssembler;

public class EntidadAssembler extends GenericAssembler<EntidadBean, EntidadDTO> {

	private static class SingletonHolder {
		private static EntidadAssembler instance = new EntidadAssembler();
	}

	public static EntidadAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(EntidadBean bean) {
		Map map = new HashMap();
		if (bean.getLocalizacion() != null) {
			LocalizacionDTO localizacion = LocalizacionAssembler.getInstance().buildDto(bean.getLocalizacion());
			map.put("localizacion", localizacion);
			map.put("idLocalizacion", localizacion.getId());
		}
		// EntidadEvaluadoraBean entidadEvaluadora =
		// bean.getEntidadEvaluadora();
		// EntidadEvaluadoraAssembler.getInstance().updateDtoNotNull(entidadEvaluadora,
		// );

		map.put("id", bean.getId());
		map.put("denominacion", bean.getDenominacion());
		map.put("cuit", bean.getCuit());
		map.put("contacto", bean.getContacto());
		map.put("descripcion", bean.getDescripcion());
		map.put("activo", bean.getActivo());
		map.put("evaluadora", bean.getEvaluadora());
		map.put("bancaria", bean.getBancaria());
		map.put("beneficiaria", bean.getBeneficiaria());

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(EntidadDTO dto) {

		Map map = new HashMap();
		LocalizacionBean localizacion = LocalizacionAssembler.getInstance().buildBean(dto.getLocalizacion());
		map.put("localizacion", localizacion);
		map.put("idLocalizacion", localizacion.getId());
		// EntidadEvaluadoraBean entidadEvaluadora =
		// EntidadEvaluadoraAssembler.getInstance().buildBean(dto.getEntidadEvaluadora());

		map.put("id", dto.getId());
		map.put("denominacion", dto.getDenominacion());
		map.put("cuit", dto.getCuit());
		map.put("contacto", dto.getContacto());
		map.put("descripcion", dto.getDescripcion());
		map.put("activo", dto.getActivo());
		map.put("evaluadora", dto.getEvaluadora());
		map.put("bancaria", dto.getBancaria());
		map.put("beneficiaria", dto.getBeneficiaria());

		return map;
	}

	@Override
	protected EntidadBean newBeanInstance() {
		return new EntidadBean();
	}

	@Override
	protected EntidadDTO newDtoInstance() {
		return new EntidadDTO();
	}
}