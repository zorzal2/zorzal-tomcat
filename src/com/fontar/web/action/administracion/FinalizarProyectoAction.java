package com.fontar.web.action.administracion;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.assembler.ProyectoCabeceraAssembler;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.pragma.util.ContextUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Finaliza un proyecto.<br>
 * Un proyecto es finalizado una vez 
 * terminados sus seguimientos.
 * @author ssanchez
 */
public class FinalizarProyectoAction extends GenericJbpmTaskAction {

	private WFProyectoServicio wfProyectoServicio;

	/**
	 * Valida que el proyecto no tenga seguimientos abiertos.
	 */
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		ProyectoDAO proyectoDAO = (ProyectoDAO)ContextUtil.getBean("proyectoDao");
		
		ProyectoBean proyecto = proyectoDAO.read(taskHelper.getIdProyecto());
		
		// Chequeo que el proyecto no tenga seguimientos abiertos
		List<String> seguimientos = proyecto.seguimientosAbiertos();
		Iterator i = seguimientos.iterator();
		while (i.hasNext()) {
			addError(messages,"app.proyecto.noFinalizarSeguimientoAbierto",i.next());
		}
	}	
	
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		ProyectoCabeceraDTO proyectoCabeceraDTO = (ProyectoCabeceraDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance, new ProyectoCabeceraAssembler());		
		
		request.setAttribute(PROYECTO, proyectoCabeceraDTO);
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		String observacion= FormUtil.getStringValue( form, "observacion");
		
		wfProyectoServicio.finalizarProyecto(idTaskInstance,observacion);
	}

	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}
}