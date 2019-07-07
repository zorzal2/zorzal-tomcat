package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.FacturacionBean;
import com.fontar.data.impl.domain.dto.FacturacionDTO;
import com.pragma.data.assembler.GenericAssembler;

public class FacturacionAssembler extends GenericAssembler<FacturacionBean, FacturacionDTO> {

	private static class SingletonHolder {
		private static FacturacionAssembler instance = new FacturacionAssembler();
	}

	public static FacturacionAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBean2DtoMap(FacturacionBean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("idEntidadBeneficiaria", bean.getIdEntidadBeneficiaria());
		map.put("numeroFacturacion", bean.getNumeroFacturacion());
		map.put("numeroPeriodico", bean.getNumeroPeriodico());

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDto2BeanMap(FacturacionDTO dto) {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("idEntidadBeneficiaria", dto.getIdEntidadBeneficiaria());
		map.put("numeroFacturacion", dto.getNumeroFacturacion());
		map.put("numeroPeriodico", dto.getNumeroPeriodico());

		return map;
	}

	@Override
	protected FacturacionBean newBeanInstance() {
		return new FacturacionBean();
	}

	@Override
	protected FacturacionDTO newDtoInstance() {
		return new FacturacionDTO();
	}

}
