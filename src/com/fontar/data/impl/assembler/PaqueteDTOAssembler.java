package com.fontar.data.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPaqueteBean;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaDTO;

/**
 * Assembler para crear un PaqueteDTO a partir de PaqueteBean
 * 
 * @author gboaglio
 */
public class PaqueteDTOAssembler {

	/**
	 * Crea dto a partir del bean
	 * @param bean
	 * @return dto
	 */
	@SuppressWarnings("unchecked")
	public static PaqueteDTO buildDto(PaqueteBean paqueteBean) {
		PaqueteDTO dto = new PaqueteDTO();

		dto.setCodigoActa(paqueteBean.getCodigoActa());
		dto.setEstado(paqueteBean.getEstado().getDescripcion());
		dto.setId(paqueteBean.getId());
		dto.setIdInstrumento(paqueteBean.getIdInstrumento());
		dto.setEsCreditoFiscal(paqueteBean.getInstrumento().aplicaCargaAlicuotaCF());
		dto.setObservacion(paqueteBean.getObservacion());
		dto.setTipo(paqueteBean.getTipo().getDescripcion());
		dto.setTipoPaquete(paqueteBean.getTipo());
		dto.setTratamiento(paqueteBean.getTratamiento());
		dto.setInstrumentoPermiteAdjudicacion(paqueteBean.getInstrumento().getPermiteAdjudicacion());
		dto.setDenominacionInstrumento(paqueteBean.getInstrumento() != null ? paqueteBean.getInstrumento().getDenominacion()
				: null);
		dto.setComision(paqueteBean.getComision() != null ? paqueteBean.getComision().getDenominacion() : null);
		dto.setDescInstrumento(paqueteBean.getInstrumento().getIdentificador());

		Collection<ProyectoPaqueteBean> proyectosPaquete = paqueteBean.getProyectosPaquete();
		Collection<ProyectoFilaDTO> proyectosDto = new ArrayList();

		for (ProyectoPaqueteBean proyectoPaquete : proyectosPaquete) {
			// TODO: SS-implementamos este filtro en el mapping o lo dejamos en
			// código??
			if (proyectoPaquete.getEsActivo()) {
				ProyectoBean proyectoBean = proyectoPaquete.getProyecto();

				ProyectoFilaDTO proyectoDto = ProyectoFilaDTOAssembler.buildDto(proyectoBean);
				proyectoDto.setProyectoBean(proyectoBean);
				proyectoDto.setPaqueteDTO(dto);
				proyectoDto.setTipoPaquete(paqueteBean.getTipo());
				proyectoDto.setTratamientoPaquete(paqueteBean.getTratamiento());

				// TODO: El resultado va encriptado
				// EvaluacionPaqueteDAO evaluacionDAO =
				// (EvaluacionPaqueteDAO)WebContextUtil.getBeanFactory().getBean("evaluacionPaqueteDao");
				// EvaluacionPaqueteBean evaluacionBean =
				// (EvaluacionPaqueteBean)
				// evaluacionDAO.findByProyectoPaqueteActivo(proyectoDto.getIdProyecto(),paqueteBean.getId()).get(0);
				// String resultado = evaluacionBean.getResultado();

				proyectosDto.add(proyectoDto);
			}
		}

		dto.setFilasProyectos(proyectosDto);

		return dto;
	}

	/**
	 * Crea proyectoBean a partir del Dto
	 * @param proyectoFilaDto
	 * @return
	 */
	public static PaqueteBean updateBean(PaqueteDTO dto) {
		PaqueteBean paqueteBean = new PaqueteBean();

		// TODO: HACER
		// paqueteBean.setId(dto.getId());

		return paqueteBean;
	}

	/**
	 * Crea lista de dto a partir de lista de beans
	 * @param List of beans
	 * @return List of dto
	 */
	public static List buildDto(List<PaqueteBean> beans) {
		List<PaqueteDTO> dtos = new ArrayList<PaqueteDTO>();

		for (PaqueteBean bean : beans) {
			dtos.add(PaqueteDTOAssembler.buildDto(bean));
		}
		return dtos;
	}
}
