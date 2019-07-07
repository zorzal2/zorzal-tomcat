package com.fontar.bus.api.workflow;

import java.util.Collection;
import java.util.Date;

import com.fontar.data.api.assembler.ProyectoRaizGeneralAssembler;
import com.fontar.data.impl.domain.codes.general.MotivoCierre;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizCerrarDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizEvaluarDTO;

/**
 * Servicios Comunes a los Proyectos
 * @author gboaglio
 */
public interface WFProyectoRaizServicio {
	public void cerrarProyecto(MotivoCierre motivo, String observacion, Long idTaskInstance);
	
	public void anularProyecto(String observacion, Long idTaskInstance);

	public void cargarEvaluacionAProyecto(EvaluacionGeneralDTO evaluacion, Long idTaskInstance);

	public ProyectoRaizEvaluarDTO obtenerDatosEvaluacion(Long idTaskInstance);
	
	public ProyectoRaizCerrarDTO obtenerDatosCierre(Long idTaskInstance);

	public DTO getProyectoRaizDTO(Long idTaskInstance, ProyectoRaizGeneralAssembler assembler);
	
	public ProyectoRaizEvaluarDTO obtenerClaseProyectoRaiz(Long idTaskInstance);
	
	public Collection obtenerEvaluaciones(Long idTaskInstance,String tipo);
	
	public void solicitarReconsideracionDeProyectoRaiz(Date fecha, String observacion, Long idTaskInstance);

	public DTO obtenerProyectoRaiz(Long idTaskInstance);
}