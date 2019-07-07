package com.fontar.web.action.proyecto.presupuesto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.dto.ProyectoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.web.action.proyecto.ProyectoBaseTaskAction;
import com.fontar.web.util.ActionUtil;
import com.pragma.jbpm.JbpmManager;

/**
 * Action de la tarea de ingreso de presupuesto a un proyecto
 * @author ssanchez
 */
public class EditarPresupuestoProyectoAction extends ProyectoBaseTaskAction {

	private static final String CURRENT_ID_PRESUPUESTO_SESSION_ATTRIBUTE = "EditarPresupuestoProyectoAction.idPresupuesto";

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}
	
	/**
	 * Carga de datos para la vista de la tarea, también se extraen los datos de
	 * contexto desde la tarea.
	 */
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		//EditarPresupuestoForm presupuestoForm = (EditarPresupuestoForm) form;
		
		ProyectoDTO proyecto = wfProyectoServicio.obtenerProyecto(idTaskInstance);
		ProyectoPresupuestoDTO presupuesto = wfProyectoServicio.obtenerPresupuesto(idTaskInstance);
		if(presupuesto==null) {
			//presupuestoForm.setIdPresupuesto(null);
			request.getSession().setAttribute(CURRENT_ID_PRESUPUESTO_SESSION_ATTRIBUTE, null);
		} else {
			//presupuestoForm.setIdPresupuesto(presupuesto.getId());
			request.getSession().setAttribute("idPresupuesto", presupuesto.getId());
			request.getSession().setAttribute(CURRENT_ID_PRESUPUESTO_SESSION_ATTRIBUTE, presupuesto.getId());
		}
		//presupuestoForm.setIdProyecto(Long.valueOf(proyecto.getId()));
		request.getSession().setAttribute("idProyecto", Long.valueOf(proyecto.getId()));
	}

	@Override
	/**
	 * Guarda el presupuesto de un proyecto
	 */
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		//Doy por terminada la tarea si efectivamente se cargó un presupuesto
		boolean presupuestoCargado;
		Object idPresupuestoInicial = request.getSession().getAttribute(CURRENT_ID_PRESUPUESTO_SESSION_ATTRIBUTE);
		ProyectoPresupuestoDTO presupuesto = wfProyectoServicio.obtenerPresupuesto(idTaskInstance);
		if(presupuesto==null) {
			presupuestoCargado = false;
		} else {
			if(presupuesto.getId()==null) {
				presupuestoCargado = false;
			} else {
				presupuestoCargado = !presupuesto.getId().equals(idPresupuestoInicial); 
			}
		}
		if(presupuestoCargado) JbpmManager.instance().getTaskInstance(idTaskInstance).end();
	}

	protected boolean useToken() {
		return false;
	}
}
