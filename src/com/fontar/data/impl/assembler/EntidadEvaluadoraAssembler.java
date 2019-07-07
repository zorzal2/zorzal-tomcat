package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.data.impl.domain.dto.EntidadEvaluadoraDTO;
import com.pragma.data.assembler.GenericAssembler;

public class EntidadEvaluadoraAssembler extends GenericAssembler<EntidadEvaluadoraBean, EntidadEvaluadoraDTO> {

	private static class SingletonHolder {
		private static EntidadEvaluadoraAssembler instance = new EntidadEvaluadoraAssembler();
	}

	public static EntidadEvaluadoraAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(EntidadEvaluadoraBean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("nroCBU", bean.getNroCBU());
		map.put("nroCuenta", bean.getNroCuenta());
		map.put("nombreBeneficiario", bean.getNombreBeneficiario());
		map.put("idEntidadBancaria", bean.getIdEntidadBancaria());
		// map.put("entidadBancaria", bean.getEntidadBancaria());
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(EntidadEvaluadoraDTO dto) {
		Map map = new HashMap();

		if (dto == null) {
			return null;
		}
		map.put("id", dto.getId());
		map.put("nroCBU", dto.getNroCBU());
		map.put("nroCuenta", dto.getNroCuenta());
		map.put("nombreBeneficiario", dto.getNombreBeneficiario());
		map.put("idEntidadBancaria", dto.getIdEntidadBancaria());
		// map.put("entidadBancaria", dto.getEntidadBancaria());
		return map;
	}

	@Override
	protected EntidadEvaluadoraBean newBeanInstance() {
		return new EntidadEvaluadoraBean();
	}

	@Override
	protected EntidadEvaluadoraDTO newDtoInstance() {
		return new EntidadEvaluadoraDTO();
	}
}