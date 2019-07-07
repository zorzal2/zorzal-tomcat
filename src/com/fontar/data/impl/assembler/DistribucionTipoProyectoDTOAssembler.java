package com.fontar.data.impl.assembler;

import java.util.ArrayList;
import java.util.List;

import com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean;
import com.fontar.data.impl.domain.dto.DistribucionTipoProyectoDTO;

/**
 * Assembler para DistribucionTipoProyecto
 * @author ssanchez
 */
public class DistribucionTipoProyectoDTOAssembler {

	/**
	 * Crea bean a partir del dto
	 */
	public static DistribucionTipoProyectoBean buildBean(DistribucionTipoProyectoDTO dto) {
		DistribucionTipoProyectoBean bean = new DistribucionTipoProyectoBean();

		bean.setId(dto.getId());
		bean.setIdInstrumento(dto.getIdInstrumento());
		bean.setIdMatrizCriterio(dto.getIdMatrizCriterio());
		bean.setIdTipoProyecto(dto.getIdTipoProyecto());
		bean.setLimite(dto.getLimiteMaximoProyecto());
		bean.setMonto(dto.getMontoTotalAsignado());
		bean.setPlazoEjecucion(dto.getPlazoEjecucion());

		return bean;
	}

	/**
	 * Crea dto a partir del bean
	 */
	public static DistribucionTipoProyectoDTO buildDto(DistribucionTipoProyectoBean bean) {
		DistribucionTipoProyectoDTO dto = new DistribucionTipoProyectoDTO();

		dto.setId(bean.getId());
		dto.setIdInstrumento(bean.getIdInstrumento());
		dto.setIdMatrizCriterio(bean.getIdMatrizCriterio());
		dto.setNombreMatrizCriterio(bean.getMatrizCriterio().getNombre());
		dto.setIdTipoProyecto(bean.getIdTipoProyecto());
		dto.setLimiteMaximoProyecto(bean.getLimite());
		dto.setMontoTotalAsignado(bean.getMonto());
		dto.setPlazoEjecucion(bean.getPlazoEjecucion());
		dto.setTipoProyecto(bean.getTipoProyecto().getNombre());

		return dto;
	}

	/**
	 * Crea lista de dtoses a partir de lista de beanes
	 * @param List of beanes
	 * @return List of dtoses
	 */
	@SuppressWarnings("unchecked")
	public static List buildListBeans(List<DistribucionTipoProyectoDTO> dtoses) {
		List beanes = new ArrayList();

		if (dtoses == null) {
			return null;
		}

		for (DistribucionTipoProyectoDTO dto : dtoses) {
			beanes.add(buildBean(dto));
		}

		return beanes;
	}

	/**
	 * Crea lista de beanes a partir de lista de dtoses
	 * @param List of beanes
	 * @return List of dtoses
	 */
	@SuppressWarnings("unchecked")
	public static List<DistribucionTipoProyectoDTO> buildListDtos(List<DistribucionTipoProyectoBean> beanes) {
		List<DistribucionTipoProyectoDTO> dtoses = new ArrayList();

		if (beanes == null) {
			return null;
		}

		for (DistribucionTipoProyectoBean bean : beanes) {
			dtoses.add(buildDto(bean));
		}

		return dtoses;
	}
}
