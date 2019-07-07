package com.fontar.data.impl.assembler;

import java.text.ParseException;
import java.util.Map;

import com.fontar.data.impl.domain.bean.EntidadIntervinientesBean;
import com.fontar.data.impl.domain.dto.EntidadIntervinientesDTO;
import com.pragma.data.assembler.GenericAssembler;

public class EntidadIntervinientesAssembler extends GenericAssembler<EntidadIntervinientesBean, EntidadIntervinientesDTO> {

	private static class SingletonHolder {
		private static EntidadIntervinientesAssembler instance = new EntidadIntervinientesAssembler();
	}

	public static EntidadIntervinientesAssembler getInstance() {
		return SingletonHolder.instance;
	}

	/***************************************************************************
	 * Crea un nuevo dto con la información base
	 **************************************************************************/
	public EntidadIntervinientesDTO buildDto(String funcion, String idEntidad, String relacion, String tipoEntidad) {
		EntidadIntervinientesDTO dto = new EntidadIntervinientesDTO();
		dto.setFuncion(funcion);
		dto.setIdEntidad(idEntidad);
		dto.setRelacion(relacion);
		dto.setTipoEntidad(tipoEntidad);
		return dto;
	}


	@Override
	public Map getBean2DtoMap(EntidadIntervinientesBean bean) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getDto2BeanMap(EntidadIntervinientesDTO dto) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected EntidadIntervinientesBean newBeanInstance() {
		return new EntidadIntervinientesBean();
	}

	@Override
	protected EntidadIntervinientesDTO newDtoInstance() {
		return new EntidadIntervinientesDTO();
	}

}
