package com.fontar.web.action.evaluacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;

public class AnularEvaluacionAction extends EvaluacionBaseTaskAction {

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

}
