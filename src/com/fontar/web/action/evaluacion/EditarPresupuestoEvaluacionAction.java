package com.fontar.web.action.evaluacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.dto.EvaluableDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;

public class EditarPresupuestoEvaluacionAction extends EvaluacionBaseTaskAction {

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}
	
	@Override
	/**
	 * Guarda el presupuesto de una evaluacion
	 */
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ProyectoPresupuestoDTO presupuesto = populatePresupuesto(form);

		// llamo al servicio que guardaría el presupuesto de un proyecto
		wfEvaluacionServicio.cargarPresupuesto(presupuesto, idTaskInstance);
	}

	@Override
	/**
	 * Muestra la pantalla para editar un presupuesto de evaluacion
	 */
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		// cargo los datos del presupuesto de la evaluacion
		ProyectoPresupuestoDTO presupuesto = wfEvaluacionServicio.obtenerPresupuesto(idTaskInstance);

		EvaluacionGeneralDTO evaluacion = wfEvaluacionServicio.obtenerEvaluacionGeneral(idTaskInstance);
		EvaluableDTO proyecto = evaluacion.getEvaluable(); 
		// revisar
		if (presupuesto.getMontoTotal() == null && presupuesto.getMontoSolicitado() == null) {
			presupuesto = wfEvaluacionServicio.obtenerPresupuestoProyecto(new Long(proyecto.getId()));
		}
		BeanUtils.copyProperty(form, "montoTotal", presupuesto.getMontoTotal());
		BeanUtils.copyProperty(form, "montoSolicitado", presupuesto.getMontoSolicitado());

		// cargo los datos del proyecto
		BeanUtils.copyProperty(form, "idProyecto", proyecto.getId());
		BeanUtils.copyProperty(form, "codigo", proyecto.getId());

		request.setAttribute("montoTotal", presupuesto.getMontoTotal());
		request.setAttribute("montoSolicitado", presupuesto.getMontoSolicitado());

		request.setAttribute("submitAction", "EditarPresupuestoEvaluacionGuardar");
		request.setAttribute("cancelAction", "CargarEvaluacion");
	}

	private ProyectoPresupuestoDTO populatePresupuesto(ActionForm form) throws Exception {
		ProyectoPresupuestoDTO presupuesto = new ProyectoPresupuestoDTO();
		presupuesto.setMontoTotal(FormUtil.getBigDecimalValue(form, "montoTotal"));
		presupuesto.setMontoSolicitado(FormUtil.getBigDecimalValue(form, "montoSolicitado"));

		return presupuesto;
	}

}
