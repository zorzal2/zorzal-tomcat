package com.fontar.web.action.presupuesto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.proyecto.AdministrarProyectoRaizServicio;
import com.fontar.bus.api.proyecto.presupuesto.ProyectoPresupuestoServicio;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.PresupuestoAdjuntoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.web.form.proyecto.presupuesto.EditarPresupuestoForm;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Action de la tarea de ingreso de presupuesto a un proyecto
 * @author llobeto
 */
public class GuardarPresupuestoAction extends BaseMappingDispatchAction {
	
	protected AdministrarProyectoRaizServicio proyectoServicio;
	protected ProyectoPresupuestoServicio presupuestoServicio;

	protected static final String SESSION_TEMP_VAR = "TempPresupuestoAdjuntoData";

	private static void borrarAdjuntoTemporario() {
		WebContextUtil.getSession().removeAttribute(SESSION_TEMP_VAR);
	}
	private static PresupuestoAdjuntoDTO getAdjuntoTemporario() {
		return (PresupuestoAdjuntoDTO)WebContextUtil.getSession().getAttribute(SESSION_TEMP_VAR);
	}
	
	public void setPresupuestoServicio(ProyectoPresupuestoServicio presupuestoServicio) {
		this.presupuestoServicio = presupuestoServicio;
	}
	public void setProyectoServicio(AdministrarProyectoRaizServicio proyectoServicio) {
		this.proyectoServicio = proyectoServicio;
	}
	
	public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		EditarPresupuestoForm presupuestoForm = (EditarPresupuestoForm) form;

		PresupuestoAdjuntoDTO presupuestoTemporario = getAdjuntoTemporario();
		borrarAdjuntoTemporario();

		Long idProyecto = presupuestoTemporario.getIdProyecto();
		presupuestoForm.setIdProyecto(idProyecto);
		
		if(presupuestoTemporario!=null) {
			ProyectoPresupuestoDTO presupuesto = presupuestoTemporario.getPresupuesto();
			presupuesto.setFundamentacion(presupuestoForm.getFundamentacion());
			//guardo el presupuesto y el adjunto
			presupuestoServicio.savePresupuesto(presupuestoTemporario);
			
			presupuestoForm.setIdPresupuesto(presupuestoTemporario.getPresupuesto().getId());
			
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}
}
