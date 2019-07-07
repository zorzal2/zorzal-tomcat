package com.fontar.data.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPaqueteBean;
import com.fontar.data.impl.domain.dto.VisualizarPaqueteDTO;
import com.fontar.data.impl.domain.dto.VisualizarProyectoFilaDTO;

/**
 * Assembler para crear un VisualizarPaqueteDTO a partir de PaqueteBean
 * 
 * @author ssanchez
 * @version 1.00, 29/11/06
 */
public class VisualizarPaqueteDTOAssembler {

	/**
	 * Crea dto a partir del bean
	 * @param bean
	 * @return dto
	 */
	@SuppressWarnings("unchecked")
	public static VisualizarPaqueteDTO buildDto(PaqueteBean paqueteBean) {
		VisualizarPaqueteDTO dto = new VisualizarPaqueteDTO();

		dto.setId(paqueteBean.getId());
		dto.setCodigoActa(paqueteBean.getCodigoActa());
		dto.setObservacion(paqueteBean.getObservacion());
		dto.setTipo(paqueteBean.getTipo().getDescripcion());

		Collection<ProyectoPaqueteBean> proyectosPaquete = paqueteBean.getProyectosPaquete();
		Collection<VisualizarProyectoFilaDTO> proyectosDto = new ArrayList();

		for (ProyectoPaqueteBean proyectoPaquete : proyectosPaquete) {
			// TODO: SS-implementamos este filtro en el mapping o lo dejamos en
			// código??
			if (proyectoPaquete.getEsActivo()) {
				ProyectoBean proyectoBean = proyectoPaquete.getProyecto();

				VisualizarProyectoFilaDTO proyectoDto = VisualizarProyectoFilaDTOAssembler.buildDto(proyectoBean);
				proyectoDto.setTipoPaquete(paqueteBean.getTipo().name());
				proyectoDto.setTratamientoPaquete(paqueteBean.getTratamiento().name());
				proyectoDto.setIdPaquete(paqueteBean.getId());

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
		
		PaqueteCabeceraAssembler assembler = new PaqueteCabeceraAssembler();
		dto.setPaqueteCabeceraDTO(assembler.buildDTO(paqueteBean));
		
		return dto;
	}

	/**
	 * Crea lista de dto a partir de lista de beans
	 * @param List of beans
	 * @return List of dto
	 */
	@SuppressWarnings("unchecked")
	public static List buildDto(List beans) {
		List dtos = new ArrayList();

		for (Iterator iter = beans.iterator(); iter.hasNext();) {
			PaqueteBean bean = (PaqueteBean) iter.next();
			dtos.add(VisualizarPaqueteDTOAssembler.buildDto(bean));
		}
		return dtos;
	}
}
