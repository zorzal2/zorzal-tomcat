package com.fontar.web.action.administracion.paquete.presupuesto;

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
import com.fontar.web.form.proyecto.presupuesto.PresupuestoUploadForm;
import com.pragma.util.URL;

public class EditarPresupuestoEvaluarPaqueteImplementationAction extends EditarPresupuestoAction {

	private final class PresupuestoEvaluarPaquetePosProcessingTask implements PresupuestoPosProcessingTask {
		private Long idEvaluacion;
		private AdministrarEvaluacionesServicio evaluacionServicio;
		
		public PresupuestoEvaluarPaquetePosProcessingTask(Long idEvaluacion, AdministrarEvaluacionesServicio evaluacionServicio) {
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
		
		PresupuestoUploadForm uploadForm = (PresupuestoUploadForm) form;
		
		ActionForward forward = super.cargar(mapping, form, request, response);
				
		Long idEvaluacion = Long.valueOf(request.getParameter("idEvaluacion"));
		
		//Seteo la accion a ejecutar en caso de que el presupuesto cargado sea 
		//efectivamente guardado
		setPosProcessingTask(
			new PresupuestoEvaluarPaquetePosProcessingTask(idEvaluacion, evaluacionServicio)
		);
				
		
		URL url = new URL();
		url.setPathFile("EditarPresupuestoEvaluarPaqueteCancelar.do");
		url.setParameter("idProyecto", uploadForm.getIdProyecto());
		url.setParameter("idPresupuesto", uploadForm.getIdPresupuesto());
		url.setParameter("idEvaluacion", idEvaluacion);
		request.setAttribute("cancelarAction", url.toString());
		return forward;
	}

	@Override
	public ActionForward ingresar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EditarPresupuestoForm presupuestoForm = (EditarPresupuestoForm) form;

		Long idEvaluacion = Long.valueOf(request.getParameter("idEvaluacion"));
		EvaluacionDTO evaluacion = evaluacionServicio.getEvaluacionDTO(idEvaluacion);
		
		//seteo Id de presupuesto
		Long idPresupuesto = evaluacion.getIdPresupuesto();
		if(idPresupuesto==null) idPresupuesto = evaluacion.getIdPresupuestoInicial();
		
		presupuestoForm.setIdPresupuesto(idPresupuesto);
		
		
		ActionForward forward = super.ingresar(mapping, form, request, response);

		URL url = new URL();
		url.setPathFile("/EditarPresupuestoEvaluarPaqueteCargar.do");
		url.setParameter("idProyecto", presupuestoForm.getIdProyecto());
		url.setParameter("idPresupuesto", presupuestoForm.getIdPresupuesto());
		url.setParameter("idEvaluacion", idEvaluacion);
		request.setAttribute("cargarAction", url.toString());
		return forward;
	}
}
