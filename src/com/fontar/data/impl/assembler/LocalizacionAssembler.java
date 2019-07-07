package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.pragma.data.assembler.GenericAssembler;

public class LocalizacionAssembler extends GenericAssembler<LocalizacionBean, LocalizacionDTO> {

	private static class SingletonHolder {
		private static LocalizacionAssembler instance = new LocalizacionAssembler();
	}

	public static LocalizacionAssembler getInstance() {
		return SingletonHolder.instance;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(LocalizacionBean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("direccion", bean.getDireccion());
		map.put("localidad", bean.getLocalidad());
		map.put("departamento", bean.getDepartamento());
		map.put("codigoPostal", bean.getCodigoPostal());
		map.put("pais", bean.getPais());
		map.put("telefono", bean.getTelefono());
		map.put("fax", bean.getFax());
		map.put("email", bean.getEmail());
		map.put("paginaWeb", bean.getPaginaWeb());
		map.put("idJurisdiccion", bean.getIdJurisdiccion());
		if(bean.getJurisdiccion() != null)
			map.put("nombreJurisdiccion", bean.getJurisdiccion().getDescripcion());

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(LocalizacionDTO dto) {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("direccion", dto.getDireccion());
		map.put("localidad", dto.getLocalidad());
		map.put("departamento", dto.getDepartamento());
		map.put("codigoPostal", dto.getCodigoPostal());
		map.put("pais", dto.getPais());
		map.put("telefono", dto.getTelefono());
		map.put("fax", dto.getFax());
		map.put("email", dto.getEmail());
		map.put("paginaWeb", dto.getPaginaWeb());
		map.put("idJurisdiccion", dto.getIdJurisdiccion());

		return map;
	}

	@Override
	protected LocalizacionBean newBeanInstance() {
		return new LocalizacionBean();
	}

	@Override
	protected LocalizacionDTO newDtoInstance() {
		return new LocalizacionDTO();
	}

}
