package com.fontar.web.action.seguimientos.seguimientos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.web.action.evaluacion.EvaluacionBaseTaskAction;
import com.fontar.web.util.ActionUtil;

public class AnularEvaluacionSeguimientoAction extends EvaluacionBaseTaskAction {

	private AdministrarSeguimientoServicio administrarSeguimientoServicio;

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		EvaluacionGeneralDTO evaluacion = wfEvaluacionServicio.obtenerEvaluacionGeneral(idTaskInstance);
		
		
		SeguimientoVisualizacionCabeceraDTO seguimientoDTO = administrarSeguimientoServicio.obtenerDatosCabeceraSeguimientoVisualizacion(evaluacion.getBitacora().getIdSeguimiento());
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, seguimientoDTO);

		// Guardo estas propiedades ya q las necesito luego
		BeanUtils.setProperty(form, "idSeguimiento", evaluacion.getBitacora().getIdSeguimiento());
		BeanUtils.setProperty(form, "idEvaluacion", evaluacion.getId());
		BeanUtils.setProperty(form, "idProyecto", evaluacion.getEvaluable().getId());

	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		String idEvaluacion = BeanUtils.getProperty(form, "idEvaluacion");
		String idProyecto = BeanUtils.getProperty(form, "idProyecto");
		String observaciones = BeanUtils.getProperty(form, "observaciones");

		EvaluacionGeneralDTO evaluacion = new EvaluacionGeneralDTO();
		evaluacion.setId(new Long(idEvaluacion));
		evaluacion.setIdProyecto(new Long(idProyecto));

		wfEvaluacionServicio.anularEvaluacion(evaluacion, observaciones, idTaskInstance);
	}

	public AdministrarSeguimientoServicio getAdministrarSeguimientoServicio() {
		return administrarSeguimientoServicio;
	}

	public void setAdministrarSeguimientoServicio(AdministrarSeguimientoServicio administrarSeguimientoServicio) {
		this.administrarSeguimientoServicio = administrarSeguimientoServicio;
	}

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}

}
