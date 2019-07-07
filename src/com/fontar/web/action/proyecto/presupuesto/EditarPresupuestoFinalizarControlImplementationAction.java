package com.fontar.web.action.proyecto.presupuesto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.proyecto.presupuesto.PresupuestoPosProcessingTask;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.PresupuestoAdjuntoDTO;
import com.fontar.web.action.presupuesto.EditarPresupuestoAction;
import com.fontar.web.form.proyecto.presupuesto.EditarPresupuestoForm;
import com.pragma.util.StringUtil;
import com.pragma.util.URL;

public class EditarPresupuestoFinalizarControlImplementationAction extends EditarPresupuestoAction {

	private static final class PresupuestoFinalizarControlPosProcessingTask implements PresupuestoPosProcessingTask {
		public String varName = null;

		public HttpSession session = null;

		public void proccess(PresupuestoAdjuntoDTO adjunto) {
			//Actualizo el presupuesto en el proyecto
			if(adjunto.getPresupuesto()!=null) {
				session.setAttribute(varName, adjunto.getPresupuesto());
			}			
		}

		public PresupuestoFinalizarControlPosProcessingTask(String varName, HttpSession session) {
			super();
			this.varName = varName;
			this.session = session;
		}
	}

	@Override
	public ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.cargar(mapping, form, request, response);
		
		//Seteo la accion a ejecutar en caso de que el presupuesto cargado sea 
		//efectivamente guardado
		PresupuestoFinalizarControlPosProcessingTask task = 
			new PresupuestoFinalizarControlPosProcessingTask(
					request.getParameter("idPresupuestoDefinitivo_randomVarName"),
					request.getSession()
			);

		setPosProcessingTask(task);

		Long idProyecto = StringUtil.nullSafeParseLong(request.getParameter("idProyecto"));
		Long idPresupuestoInicial = StringUtil.nullSafeParseLong(request.getParameter("idPresupuestoInicial"));

		URL url = new URL();
		url.setPathFile("/EditarPresupuestoFinalizarControlCancelar.do");
		url.setParameter("idProyecto", idProyecto);
		url.setParameter("idPresupuestoInicial", idPresupuestoInicial);
		request.setAttribute("cancelarAction", url.toString());
		return forward;
	}

	@Override
	public ActionForward ingresar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EditarPresupuestoForm presupuestoForm = (EditarPresupuestoForm) form;


		if(presupuestoForm.getIdProyecto()==null) {
			//asumo que tengo id de proyecto en el request
			presupuestoForm.setIdProyecto((Long)request.getAttribute("idProyecto"));
			presupuestoForm.setIdPresupuesto((Long)request.getAttribute("idPresupuestoInicial"));
		}
		
		ActionForward forward = super.ingresar(mapping, form, request, response);

		URL url = new URL();
		url.setPathFile("/EditarPresupuestoFinalizarControlCargar.do");
		url.setParameter("idProyecto", presupuestoForm.getIdProyecto());
		url.setParameter("idPresupuestoInicial", presupuestoForm.getIdPresupuesto());
		url.setParameter("idPresupuestoDefinitivo_randomVarName", request.getParameter("idPresupuestoDefinitivo_randomVarName"));
		request.setAttribute("cargarAction", url.toString());
		return forward;
	}
}
