package com.fontar.web.action.evaluacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.dto.VisualizarEvaluacionProyectoDTO;
import com.pragma.util.FormUtil;

public class AutorizarEvaluacionAction extends EvaluacionBaseTaskAction {

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		boolean autorizada = BeanUtils.getProperty(form, "autorizaEvaluacion").equals("1");
		String fundamentacion = FormUtil.getStringValue(form, "fundamentacion");
		
		wfEvaluacionServicio.autorizarEvaluacion(autorizada, fundamentacion, idTaskInstance);
	}
	
	protected  VisualizarEvaluacionProyectoDTO decorate( VisualizarEvaluacionProyectoDTO dto ){
		dto.setResumido();
		return dto;
	}
}
