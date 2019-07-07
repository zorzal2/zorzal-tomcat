package com.pragma.jbpm;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.seguridad.AuthenticationService;
import com.fontar.web.form.AdjuntoUploadForm;
import com.pragma.web.action.GenericJbpmTaskAction;

public class JbpmManagerServiceImpl implements JbpmManagerService {

	private JbpmManager jbpmManager;

	private AuthenticationService authenticationService;
	
	public JbpmManager getJbpmManager() {
		return jbpmManager;
	}

	public void setJbpmManager(JbpmManager jbpmManager) {
		this.jbpmManager = jbpmManager;
	}
	
	
	public AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public void actualizarPermisosTareas() {
		this.jbpmManager.actualizarPermisosTareas();
	}
	
	
	public void start(Long idTaskInstance) {
		JbpmContext context = JbpmContext.getCurrentJbpmContext();
		TaskInstance taskInstance = (TaskInstance) context.loadTaskInstanceForUpdate(idTaskInstance);
		if (taskInstance.getStart() == null) {
			taskInstance.start(this.authenticationService.getUserId());
		}
		
	}

	public void executeEjecutarYTerminarTarea(GenericJbpmTaskAction action, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long currentTaskInstanceId) {
		this.start(currentTaskInstanceId);
		try {
			action.ejecutarYTerminarTarea(mapping, form, request, response, messages, currentTaskInstanceId);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public void deploy(AdjuntoUploadForm upload){
		try {
			JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
			InputStream inputStream = upload.getContent().getInputStream();
			ZipInputStream zipInputStream = new ZipInputStream(inputStream);
			ProcessDefinition processDefinition = ProcessDefinition.parseParZipInputStream(zipInputStream);
			jbpmContext.deployProcessDefinition(processDefinition);
			inputStream.close();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
