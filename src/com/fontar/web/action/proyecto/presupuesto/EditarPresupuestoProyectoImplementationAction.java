package com.fontar.web.action.proyecto.presupuesto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.proyecto.presupuesto.PresupuestoPosProcessingTask;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.PresupuestoAdjuntoDTO;
import com.fontar.web.action.presupuesto.EditarPresupuestoAction;

public class EditarPresupuestoProyectoImplementationAction extends EditarPresupuestoAction {

	@Override
	public ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.cargar(mapping, form, request, response);
		

		//Seteo la accion a ejecutar en caso de que el presupuesto cargado sea 
		//efectivamente guardado
		setPosProcessingTask(
			new PresupuestoPosProcessingTask() {
				public void proccess(PresupuestoAdjuntoDTO adjunto) {
					//Actualizo el presupuesto en el proyecto
					if(adjunto.getPresupuesto()!=null) {
						proyectoServicio.actualizarPresupuestoActual(adjunto.getIdProyecto(), adjunto.getPresupuesto());
					}			
				}			
			}
		);
		
		request.setAttribute("guardarAction", "/GuardarPresupuestoProyecto.do");
		request.setAttribute("cancelarAction", "/EditarPresupuestoProyectoImplementationCancelar.do");
		
		request.setAttribute("showProjectHeader", Boolean.TRUE);
		return forward;
	}

	@Override
	public ActionForward ingresar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.ingresar(mapping, form, request, response);
		request.setAttribute("cargarAction", "/EditarPresupuestoProyectoImplementationCargar.do");
		request.setAttribute("cerrarAction", "/EditarPresupuestoProyectoImplementationCerrar.do");
		
		request.setAttribute("showProjectHeader", Boolean.TRUE);
		return forward;
	}

}
