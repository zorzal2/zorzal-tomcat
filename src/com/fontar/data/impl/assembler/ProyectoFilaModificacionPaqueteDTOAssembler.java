package com.fontar.data.impl.assembler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaModificacionPaqueteDTO;
import com.pragma.util.ContextUtil;

/**
 * Assembler para crear un ProyectoFilaDTO a partir de ProyectoPaqueteBean
 * 
 * @author gboaglio
 */
public class ProyectoFilaModificacionPaqueteDTOAssembler {

	/**
	 * Crea dto a partir del bean
	 * @param bean
	 * @return dto
	 * @author ssanchez
	 */
	public static ProyectoFilaModificacionPaqueteDTO buildDto(ProyectoBean proyectoBean, boolean activo) {
		ProyectoFilaModificacionPaqueteDTO dto = new ProyectoFilaModificacionPaqueteDTO();

		ProyectoDatosBean datosBean = proyectoBean.getProyectoDatos();
		dto.setIdProyecto(proyectoBean.getId());
		dto.setIdInstrumento(proyectoBean.getIdInstrumento());
		dto.setEntidadBeneficiaria(datosBean.getEntidadBeneficiaria().getDenominacion());
		dto.setTitulo(datosBean.getTitulo());
		
		//Chequeo si el usuario tiene permiso para obtener el dato.
		AdministrarProyectoServicio administrarProyectoServicio = (AdministrarProyectoServicio)ContextUtil.getBean("administrarProyectoService");
		dto.setRecomendacion(administrarProyectoServicio.getRecomendacionSiEsAccesible(proyectoBean));
		
		dto.setEsActivo(activo);
		dto.setCodigo(proyectoBean.getCodigo());

		ArrayList<EvaluacionResumenDTO> evaluaciones = new ArrayList<EvaluacionResumenDTO>();

		for (EvaluacionBean evaluacionBean : proyectoBean.getEvaluaciones()) {
			if (!evaluacionBean.esAnulada()) {

				EvaluacionResumenDTO evalDto = new EvaluacionResumenDTO();

				evalDto.setResultadoEvaluacion(evaluacionBean.getResultadoEvaluacion());
				evalDto.setTipoEvaluacion(evaluacionBean.getTipo());

				evaluaciones.add(evalDto);
			}
		}

		dto.setEvaluaciones(evaluaciones);

		ProyectoPresupuestoBean presupuesto = proyectoBean.getProyectoPresupuestoOriginal();
		if (presupuesto != null) {
			dto.setMontoSolicitado(presupuesto.getMontoSolicitado());
		}

		BigDecimal montoAprobado = proyectoBean.getMontoAprobado();
		if (montoAprobado != null) {
			dto.setMontoAprobado(montoAprobado);
		}

		BigDecimal montoAdjudicado = proyectoBean.getMontoAdjudicado();
		if (montoAdjudicado != null) {
			dto.setMontoAdjudicado(montoAdjudicado);
		}

		return dto;
	}

	/**
	 * Crea lista de dto a partir de lista de beans
	 * 
	 * @param List of beans : Lista de ProyectoBean's que se va usar para
	 * obtener una lista de DTO's
	 * 
	 * @param boolean : activo indica el seteo que se le dará al atributo
	 * "esActivo" a TODOS los DTO que se van a crear a partir de los beans
	 * pasados como parámetro
	 * 
	 * @return List of dto
	 */
	@SuppressWarnings("unchecked")
	public static List buildDto(List beans, boolean activo) {
		List dtos = new ArrayList();
		if (beans != null) {
			for (Iterator iter = beans.iterator(); iter.hasNext();) {
				ProyectoBean bean = (ProyectoBean) iter.next();
				dtos.add(ProyectoFilaModificacionPaqueteDTOAssembler.buildDto(bean, activo));
			}
		}
		return dtos;
	}
}
