package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.EmpleoPermanenteBean;
import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.pragma.data.assembler.GenericAssembler;

public class EmpleoPermanenteAssembler extends GenericAssembler<EmpleoPermanenteBean, EmpleoPermanenteDTO> {

	private static class SingletonHolder {
		private static EmpleoPermanenteAssembler instance = new EmpleoPermanenteAssembler();
	}

	public static EmpleoPermanenteAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(EmpleoPermanenteBean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("cantidadOperariosCalificados", bean.getCantidadOperariosCalificados());
		map.put("cantidadOperariosNoCalificados", bean.getCantidadOperariosNoCalificados());
		map.put("cantidadProfesionales", bean.getCantidadProfesionales());
		map.put("cantidadTecnicos", bean.getCantidadTecnicos());
		if(bean.getCantidadOperariosCalificados() != null) {
			map.put("cantidadOperariosCalificadosStr", bean.getCantidadOperariosCalificados().toString());
		}
		if(bean.getCantidadOperariosNoCalificados() != null) {
			map.put("cantidadOperariosNoCalificadosStr", bean.getCantidadOperariosNoCalificados().toString());
		}
		if(bean.getCantidadProfesionales() != null) {
			map.put("cantidadProfesionalesStr", bean.getCantidadProfesionales().toString());
		}
		if(bean.getCantidadTecnicos() != null) {
			map.put("cantidadTecnicosStr", bean.getCantidadTecnicos().toString());
		}
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(EmpleoPermanenteDTO dto) {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("cantidadOperariosCalificados", dto.getCantidadOperariosCalificados());
		map.put("cantidadOperariosNoCalificados", dto.getCantidadOperariosNoCalificados());
		map.put("cantidadProfesionales", dto.getCantidadProfesionales());
		map.put("cantidadTecnicos", dto.getCantidadTecnicos());

		return map;
	}

	@Override
	protected EmpleoPermanenteBean newBeanInstance() {
		return new EmpleoPermanenteBean();
	}

	@Override
	protected EmpleoPermanenteDTO newDtoInstance() {
		return new EmpleoPermanenteDTO();
	}

}
