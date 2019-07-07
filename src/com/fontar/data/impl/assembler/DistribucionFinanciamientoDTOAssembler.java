package com.fontar.data.impl.assembler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fontar.data.impl.domain.bean.DistribucionFinanciamientoBean;
import com.fontar.data.impl.domain.dto.DistribucionFinanciamientoDTO;

/**
 * Assembler para DistribucionFinanciamiento
 * @author ssanchez
 */
public class DistribucionFinanciamientoDTOAssembler {

	/**
	 * Crea bean a partir del dto
	 */
	public static DistribucionFinanciamientoBean buildBean(DistribucionFinanciamientoDTO dto) {
		DistribucionFinanciamientoBean bean = new DistribucionFinanciamientoBean();

		bean.setId(dto.getId().equals("") ? null : new Long(dto.getId()));
		bean.setIdJurisdiccion((dto.getIdJurisdiccion() == null || dto.getIdJurisdiccion().equals("")) ? null
				: new Long(dto.getIdJurisdiccion()));
		bean.setIdRegion((dto.getIdRegion() == null || dto.getIdRegion().equals("")) ? null
				: new Long(dto.getIdRegion()));
		bean.setIdInstrumento((dto.getIdInstrumento() == null || dto.getIdInstrumento().equals("")) ? null
				: new Long(dto.getIdInstrumento()));
		bean.setMonto((dto.getMonto() == null || dto.getMonto().equals("")) ? null : new BigDecimal(dto.getMonto()));
		bean.setPorcentaje((dto.getPorcentaje() == null || dto.getPorcentaje().equals("")) ? null
				: new BigDecimal(dto.getPorcentaje()));

		return bean;
	}

	/**
	 * Crea dto a partir del bean
	 */
	public static DistribucionFinanciamientoDTO buildDto(DistribucionFinanciamientoBean bean) {
		DistribucionFinanciamientoDTO dto = new DistribucionFinanciamientoDTO();

		dto.setId(bean.getId() == null ? null : bean.getId().toString());
		dto.setIdJurisdiccion(bean.getIdJurisdiccion() == null ? null : bean.getIdJurisdiccion().toString());
		dto.setIdRegion(bean.getIdRegion() == null ? null : bean.getIdRegion().toString());
		dto.setIdInstrumento(bean.getIdInstrumento() == null ? null : bean.getIdInstrumento().toString());
		dto.setMonto(bean.getMonto() == null ? null : bean.getMonto().toString());
		dto.setPorcentaje(bean.getPorcentaje() == null ? null : bean.getPorcentaje().toString());
		dto.setNombre(bean.getIdJurisdiccion() != null ? bean.getJurisdiccion().getDescripcion()
				: bean.getRegion().getNombre());

		return dto;
	}

	/**
	 * Crea lista de beanes a partir de lista de dtoses
	 * @param List of beanes
	 * @return List of dtoses
	 */
	@SuppressWarnings("unchecked")
	public static List buildListBeans(List<DistribucionFinanciamientoDTO> dtoses) {
		List beanes = new ArrayList();

		for (DistribucionFinanciamientoDTO dto : dtoses) {
			beanes.add(buildBean(dto));
		}

		return beanes;
	}

	/**
	 * Crea lista de dtoses a partir de lista de beanes
	 * @param List of beanes
	 * @return List of dtoses
	 */
	@SuppressWarnings("unchecked")
	public static List<DistribucionFinanciamientoDTO> buildListDtos(List<DistribucionFinanciamientoBean> beanes) {
		List<DistribucionFinanciamientoDTO> dtoses = new ArrayList();

		for (DistribucionFinanciamientoBean bean : beanes) {
			dtoses.add(buildDto(bean));
		}

		return dtoses;
	}
}
