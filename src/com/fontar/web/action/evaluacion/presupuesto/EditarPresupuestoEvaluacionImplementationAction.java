package com.fontar.web.action.evaluacion.presupuesto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.evaluacion.AdministrarEvaluacionesServicio;
import com.fontar.bus.api.proyecto.presupuesto.PresupuestoPosProcessingTask;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.PresupuestoAdjuntoDTO;
import com.fontar.web.action.presupuesto.EditarPresupuestoAction;
import com.fontar.web.form.proyecto.presupuesto.EditarPresupuestoForm;

public class EditarPresupuestoEvaluacionImplementationAction extends EditarPresupuestoAction {

	private final class PresupuestoEvaluacionPosProcessingTask implements PresupuestoPosProcessingTask {
		private Long idEvaluacion;
		private AdministrarEvaluacionesServicio evaluacionServicio;
		
		public PresupuestoEvaluacionPosProcessingTask(Long idEvaluacion, AdministrarEvaluacionesServicio evaluacionServicio) {
			this.idEvaluacion = idEvaluacion;
			this.evaluacionServicio = evaluacionServicio;
		}

		public void proccess(PresupuestoAdjuntoDTO adjunto) {
			if(adjunto.getPresupuesto()!=null) {
				this.evaluacionServicio.savePresupuestoId(this.idEvaluacion, adjunto.getPresupuesto().getId());
			}
		}
	}

	private AdministrarEvaluacionesServicio evaluacionServicio = null;
	
	public AdministrarEvaluacionesServicio getEvaluacionServicio() {
		return evaluacionServicio;
	}
	
	public void setEvaluacionServicio(AdministrarEvaluacionesServicio evaluacionServicio) {
		this.evaluacionServicio = evaluacionServicio;
	}
	
	@Override
	public ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.cargar(mapping, form, request, response);
		
		Long idEvaluacion = Long.valueOf(request.getParameter("idEvaluacion"));

		//Seteo la accion a ejecutar en caso de que el presupuesto cargado sea 
		//efectivamente guardado
		setPosProcessingTask(
			new PresupuestoEvaluacionPosProcessingTask(idEvaluacion, evaluacionServicio)
		);
				
		request.setAttribute("cancelarAction", "/EditarPresupuestoEvaluacionCancelar.do?idEvaluacion="+idEvaluacion);
		return forward;
	}

	@Override
	public ActionForward ingresar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EditarPresupuestoForm presupuestoForm = (EditarPresupuestoForm) form;

		if(presupuestoForm.getIdProyecto()==null) {
			//chequeo si ya tengo id de proyecto en el request
			if(request.getAttribute("idProyecto")!=null) {
				presupuestoForm.setIdProyecto((Long)request.getAttribute("idProyecto"));
				presupuestoForm.setIdPresupuesto((Long)request.getAttribute("idPresupuesto"));
			} else {
				Long idEvaluacion = Long.valueOf(request.getParameter("idEvaluacion"));
				EvaluacionDTO evaluacion = evaluacionServicio.getEvaluacionDTO(idEvaluacion);
				
				//seteo Id de presupuesto
				Long idPresupuesto = evaluacion.getIdPresupuesto();
				if(idPresupuesto==null) idPresupuesto = evaluacion.getIdPresupuestoInicial();
				
				presupuestoForm.setIdPresupuesto(idPresupuesto);
				
				//seteo Id de proyecto
				presupuestoForm.setIdProyecto(evaluacion.getIdProyecto());
			}
		}
		
		ActionForward forward = super.ingresar(mapping, form, request, response);

		request.setAttribute("cargarAction", "/EditarPresupuestoEvaluacionCargar.do?idEvaluacion="+request.getParameter("idEvaluacion"));
		return forward;
	}
}
