package com.fontar.data.impl.assembler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaDTO;
import com.fontar.seguridad.EncryptedObject;
import com.pragma.util.ContextUtil;

/**
 * Assembler para crear un ProyectoFilaDTO a partir de ProyectoPaqueteBean
 * 
 * @author gboaglio
 */
public class ProyectoFilaDTOAssembler {

	/**
	 * Crea dto a partir del bean
	 * @param bean
	 * @return dto
	 */
	public static ProyectoFilaDTO buildDto(ProyectoBean proyectoBean) {
		ProyectoFilaDTO dto = new ProyectoFilaDTO();
		dto.setProyectoBean(proyectoBean);
		dto.setIdProyecto(proyectoBean.getId());
		dto.setProyecto(proyectoBean.getCodigo());
		dto.setPermiteAdjudicacionInstrumento(proyectoBean.getInstrumento().getPermiteAdjudicacion());
		
		ProyectoDatosBean proyectoDatos = proyectoBean.getProyectoDatos();
		EntidadBeneficiariaBean entidadBeneficiaria = proyectoDatos.getEntidadBeneficiaria(); 
		dto.setNombreEntidad(entidadBeneficiaria.getDenominacion());

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


		//Chequeo si el usuario tiene permiso para obtener el dato.
		AdministrarProyectoServicio administrarProyectoServicio = (AdministrarProyectoServicio)ContextUtil.getBean("administrarProyectoService");
		EncryptedObject recomendacionONull = administrarProyectoServicio.getRecomendacionSiEsAccesible(proyectoBean);
		if(recomendacionONull==null) dto.setRecomendacion(null);
		else dto.setRecomendacion((Recomendacion) recomendacionONull.getObject());
		
		dto.setTitulo(proyectoBean.getProyectoDatos().getTitulo());

		dto.setTipoPresupuesto(proyectoBean.getInstrumento().getMatrizPresupuesto().getTipo());
		
		/* Presupuesto */
		
		//Alicuota
		BigDecimal alicuota = proyectoBean.getPorcentajeAlicuotaSolicitada();
		if (alicuota != null) {
			dto.setPorcentajeAlicuotaSolicitada(alicuota);
		}

		alicuota = proyectoBean.getPorcentajeAlicuotaAdjudicada();
		if (alicuota != null) {
			dto.setPorcentajeAlicuotaAdjudicada(alicuota);
		}
		//Solicitados
		ProyectoPresupuestoBean presupuesto = proyectoBean.getProyectoPresupuestoOriginal();
		if (presupuesto != null) {
			dto.setTotalSolicitado(presupuesto.getMontoTotal());

			if(!proyectoBean.getInstrumento().aplicaCargaAlicuotaCF())
				dto.setBeneficioFONTARSolicitado(presupuesto.getMontoSolicitado());
		}
		return dto;
	}

	/**
	 * Crea lista de dto a partir de lista de beans
	 * @param List of beans
	 * @return List of dto
	 */
	public static List buildDto(List<ProyectoBean> beans) {
		List<ProyectoFilaDTO> dtos = new ArrayList<ProyectoFilaDTO>();

		for (ProyectoBean bean : beans) {
			dtos.add(ProyectoFilaDTOAssembler.buildDto(bean));
		}
		return dtos;
	}
}
