package com.fontar.data.impl.assembler;

import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.EvaluacionSeguimientoBean;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoDTO;

public class EvaluacionSeguimientoAssembler extends EvaluacionGeneralAssembler {

	
	private static class SingletonHolder {
		private static EvaluacionSeguimientoAssembler instance = new EvaluacionSeguimientoAssembler();
	}

	public static EvaluacionSeguimientoAssembler getInstance() {
		return SingletonHolder.instance;
	}

	public EvaluacionSeguimientoDTO buildDto(EvaluacionSeguimientoBean bean) {
		EvaluacionSeguimientoDTO dto = (EvaluacionSeguimientoDTO) super.buildDto(bean);
		dto.setIdSeguimiento(bean.getIdSeguimiento());
		return dto;
	}

	public EvaluacionSeguimientoBean buildBean(EvaluacionSeguimientoDTO dto) {
		EvaluacionSeguimientoBean bean = (EvaluacionSeguimientoBean) super.buildBean(dto);
		bean.setIdSeguimiento(dto.getIdSeguimiento());
		return bean;
	}

	@Override
	public EvaluacionGeneralBean buildBean(EvaluacionGeneralDTO dto) {
		if(dto instanceof EvaluacionSeguimientoDTO) return buildBean((EvaluacionSeguimientoDTO)dto); 
		throw new UnsupportedOperationException();
	}

	@Override
	public EvaluacionGeneralDTO buildDto(EvaluacionGeneralBean bean) {
		if(bean instanceof EvaluacionSeguimientoBean) return buildDto((EvaluacionSeguimientoBean)bean);
		throw new UnsupportedOperationException();
	}

	public void updateBeanNotNull(EvaluacionSeguimientoBean bean, EvaluacionSeguimientoDTO dto) {
		super.updateBeanNotNull(bean, dto);
		bean.setIdSeguimiento(dto.getIdSeguimiento());
	}

	public void updateDtoNotNull(EvaluacionSeguimientoDTO dto, EvaluacionSeguimientoBean bean) {
		super.updateDtoNotNull(dto, bean);
		dto.setIdSeguimiento(bean.getIdSeguimiento());
	}

	@Override
	protected EvaluacionGeneralBean newBeanInstance() {
		return new EvaluacionSeguimientoBean();
	}

	@Override
	protected EvaluacionGeneralDTO newDtoInstance() {
		return new EvaluacionSeguimientoDTO();
	}
}
