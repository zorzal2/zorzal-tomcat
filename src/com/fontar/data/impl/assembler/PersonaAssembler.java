package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.pragma.data.assembler.GenericAssembler;

public class PersonaAssembler extends GenericAssembler<PersonaBean, PersonaDTO> {

	private static class SingletonHolder {
		private static PersonaAssembler instance = new PersonaAssembler();
	}

	public static PersonaAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(PersonaBean bean) {

		Map map = new HashMap();

		LocalizacionDTO localizacion = new LocalizacionDTO();
		
		// FF: check null de localización
		if (bean.getLocalizacion() != null){
			localizacion = LocalizacionAssembler.getInstance().buildDto(bean.getLocalizacion());
		}

		map.put("id", bean.getId());
		map.put("tituloGrado", bean.getTituloGrado());
		map.put("sexo", bean.getSexo());
		map.put("nombre", bean.getNombre());
		map.put("cuit", bean.getCuit());
		map.put("localizacion", localizacion);
		map.put("cargo", bean.getCargo());
		map.put("activo", bean.getActivo());
		map.put("observacion", bean.getObservacion());
		map.put("esEvaluador", bean.getEsEvaluador());

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(PersonaDTO dto) {

		Map map = new HashMap();

		LocalizacionBean localizacion = LocalizacionAssembler.getInstance().buildBean(dto.getLocalizacion());

		map.put("id", dto.getId());
		map.put("tituloGrado", dto.getTituloGrado());
		map.put("sexo", dto.getSexo());
		map.put("nombre", dto.getNombre());
		map.put("cuit", dto.getCuit());
		map.put("localizacion", localizacion);
		map.put("cargo", dto.getCargo());
		map.put("activo", dto.getActivo());
		map.put("observacion", dto.getObservacion());
		map.put("esEvaluador", dto.getEsEvaluador());

		return map;
	}

	@Override
	protected PersonaBean newBeanInstance() {
		return new PersonaBean();
	}

	@Override
	protected PersonaDTO newDtoInstance() {
		return new PersonaDTO();
	}
	
}
