package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.dto.ProyectoAgregarDTO;
import com.pragma.data.assembler.GenericAssembler;

/**
 * 
 * @author gboaglio
 * 
 */

public class ProyectoAgregarAssembler extends GenericAssembler<PresentacionConvocatoriaBean, ProyectoAgregarDTO> {

	private static class SingletonHolder {
		private static ProyectoAgregarAssembler instance = new ProyectoAgregarAssembler();
	}

	public static ProyectoAgregarAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(PresentacionConvocatoriaBean presentacionBean) {

		Map map = new HashMap();
				
		map.put("codigoPresentacion", presentacionBean.getCodigo());
		map.put("idPresentacion", presentacionBean.getId());		
		map.put("entidadBeneficiariaOrigen", presentacionBean.getNombreEntidad());
	    	    
		InstrumentoBean instrumentoBean = presentacionBean.getInstrumento();
		
		if (instrumentoBean != null) {
			map.put("permiteFinanciamientoBancario", instrumentoBean.getPermiteFinanciamientoBancario());
			map.put("idInstrumento", instrumentoBean.getId());
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(ProyectoAgregarDTO dto) {
		Map map = new HashMap();

		return map;
	}

	@Override
	protected PresentacionConvocatoriaBean newBeanInstance() {
		return new PresentacionConvocatoriaBean();
	}

	@Override
	protected ProyectoAgregarDTO newDtoInstance() {
		return new ProyectoAgregarDTO();
	}

}
