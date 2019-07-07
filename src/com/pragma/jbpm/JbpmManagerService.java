package com.pragma.jbpm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.web.form.AdjuntoUploadForm;
import com.pragma.web.action.GenericJbpmTaskAction;

public interface JbpmManagerService {

	void actualizarPermisosTareas();

	public void start(Long idTaskInstance);

	void executeEjecutarYTerminarTarea(GenericJbpmTaskAction action, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long currentTaskInstanceId);
	
	public void deploy(AdjuntoUploadForm upload);
}