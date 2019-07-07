package com.fontar.data.impl.assembler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDTO;
import com.fontar.data.impl.domain.dto.VisualizarProyectoFilaDTO;
import com.pragma.util.ContextUtil;

/**
 * Assembler para crear un VisualizarProyectoFilaDTO a partir de
 * ProyectoPaqueteBean
 * 
 * @author ssanchez
 * @version 1.00, 29/11/06
 */
public class VisualizarProyectoFilaDTOAssembler {

	/**
	 * Crea dto a partir del bean
	 * @param bean
	 * @return dto
	 */
	public static VisualizarProyectoFilaDTO buildDto(ProyectoBean proyectoBean) {
		VisualizarProyectoFilaDTO dto = new VisualizarProyectoFilaDTO();

		dto.setIdProyecto(proyectoBean.getId());
		dto.setProyecto(proyectoBean.getCodigo());
		dto.setPermiteAdjudicacionInstrumento(proyectoBean.getInstrumento().getPermiteAdjudicacion());
		dto.setNombreEntidad(proyectoBean.getProyectoDatos().getEntidadBeneficiaria().getDenominacion());

		ArrayList<EvaluacionResumenDTO> evaluaciones = new ArrayList<EvaluacionResumenDTO>();

		for (EvaluacionBean evaluacionBean : proyectoBean.getEvaluaciones()) {
			if (!evaluacionBean.esAnulada()) {

				EvaluacionResumenDTO evalDto = new EvaluacionResumenDTO();
				evalDto.setResultadoEvaluacion( evaluacionBean.getResultadoEvaluacion());
				evalDto.setTipoEvaluacion(evaluacionBean.getTipo());
				evaluaciones.add(evalDto);
			}
		}

		dto.setEvaluaciones(evaluaciones);

		BigDecimal montoAprobado = proyectoBean.getMontoAprobado();
		if (montoAprobado != null) {
			dto.setMontoAprobado(montoAprobado);
		}

		BigDecimal montoAdjudicado = proyectoBean.getMontoAdjudicado();
		if (montoAdjudicado != null) {
			dto.setMontoAdjudicado(montoAdjudicado);
		}

		ProyectoPresupuestoBean presupuesto = proyectoBean.getProyectoPresupuestoOriginal();
		if (presupuesto != null) {
			dto.setMontoSolicitado(presupuesto.getMontoSolicitado());
		}

		BigDecimal alicuota = proyectoBean.getPorcentajeAlicuotaSolicitada();
		if (alicuota != null) {
			dto.setPorcentajeSolicitado(alicuota);
		}

		//Chequeo si el usuario tiene permiso para obtener el dato.
		AdministrarProyectoServicio administrarProyectoServicio = (AdministrarProyectoServicio)ContextUtil.getBean("administrarProyectoService");
		dto.setRecomendacion(administrarProyectoServicio.getRecomendacionSiEsAccesible(proyectoBean));
		
		// TODO: SS-cuando se almacene el idProyectoPaquete se debe obtener
		// dto.setResultado(proyectoBean.getEvaluacionPaquete().getResultado());
		dto.setTitulo(proyectoBean.getProyectoDatos().getTitulo());

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
			ProyectoBean bean = (ProyectoBean) iter.next();
			dtos.add(VisualizarProyectoFilaDTOAssembler.buildDto(bean));
		}

		return dtos;
	}
}
