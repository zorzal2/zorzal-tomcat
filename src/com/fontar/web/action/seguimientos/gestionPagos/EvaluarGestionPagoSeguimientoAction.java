package com.fontar.web.action.seguimientos.gestionPagos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.proyecto.desembolso.ProyectoDesembolsoService;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.data.impl.domain.dto.CronogramaDeDesembolsosDTO;
import com.fontar.jbpm.manager.SeguimientoTaskInstanceManager;
import com.fontar.web.action.seguimientos.seguimientos.SeguimientoBaseTaskAction;
import com.pragma.util.FormUtil;

/**
 * Permite modificar los montos aprobados (de evaluación)
 * de las rendiciones de un seguimiento.<br>
 * Además determina si el seguimiento pasa a estado
 * <i>Gestionado</i>, <i>No Gestionado</i> o por revaluación al
 * estado <i>Evaluación</i>.<br> 
 * @author ssanchez
 */
public class EvaluarGestionPagoSeguimientoAction extends SeguimientoBaseTaskAction {

	private ProyectoDesembolsoService proyectoDesembolsoService;
	
	public void setProyectoDesembolsoService(ProyectoDesembolsoService proyectoDesembolsoService) {
		this.proyectoDesembolsoService = proyectoDesembolsoService;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		
		Long idSeguimiento = taskHelper.getIdSeguimiento();
		setIdSeguimiento(request,idSeguimiento);
		
		//Pongo los desembolsos para mostrar el cronograma
		
		CronogramaDeDesembolsosDTO cronograma = proyectoDesembolsoService.obtenerCronogramaParaEvaluarGestionDePago(idSeguimiento);
		request.setAttribute("cronograma", cronograma);
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		String observacion = FormUtil.getStringValue(form,"observacion");
		String estado = FormUtil.getStringValue(form,"estado");
		EstadoSeguimiento estadoSeguimiento = EstadoSeguimiento.valueOf(estado);
		
		wfSeguimientoServicio.cargarGestionPago(idTaskInstance,estadoSeguimiento,observacion);
	}
}