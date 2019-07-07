package com.fontar.web.action.workflow;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.NullArgumentException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.jbpm.util.JbpmConstants;
import com.fontar.seguridad.AuthenticationService;
import com.fontar.web.form.AdjuntoUploadForm;
import com.pragma.jbpm.JbpmManager;
import com.pragma.jbpm.JbpmManagerService;
import com.pragma.toolbar.Toolbar;
import com.pragma.toolbar.util.ToolbarRegister;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.util.ContextUtil;
import com.pragma.web.NavigationManager;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * 
 * @author fferrara
 * 
 */

public class JbpmManagerAction extends BaseMappingDispatchAction {

	JbpmManager jbpmManager;

	JbpmManagerService service;
	
	public JbpmManagerService getService() {
		return service;
	}

	public void setService(JbpmManagerService service) {
		this.service = service;
	}

	public JbpmManager getJbpmManager() {
		return jbpmManager;
	}

	public void setJbpmManager(JbpmManager jbpmManager) {
		this.jbpmManager = jbpmManager;
	}

	/**
	 * Muestra en pantalla los procesos definidos en el esquema
	 */
	public ActionForward procesosDefinidos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List processDefinitions = jbpmManager.findProcessDefinitions();
		request.setAttribute("processDefinitions", processDefinitions);

		return mapping.findForward("success");

	}

	/**
	 * Muestra en pantalla todas las tareas instanciadas del JBPM, con accion de
	 * finalización y signal
	 */
	public ActionForward tareas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List taskInstances = jbpmManager.findAllTaskInstances();
		request.setAttribute("taskInstances", taskInstances);

		return mapping.findForward("success");
	}

	/**
	 * Muestra la bandeja de entrada del usuario
	 */
	public ActionForward bandeja(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Session session = jbpmManager.getContext().getSession();
		String userName = request.getUserPrincipal().getName();

			//(session.createQuery("select distinct o from org.jbpm.taskmgmt.exe.TaskInstance o join o.pooledActors pa where ((o.actorId = 'fferrara' and o.isOpen = true) or (o.actorId is null and o.isOpen=true and pa.actorId = 'fferrara') and o.task.name || o.token.processInstance.processDefinition.name = 'Cerrar ProyectoProyecto')")).list()
		
		CollectionHandler collectionHandler = new CollectionHandler(); 
		
		Toolbar toolbar = new Toolbar(request, (ToolbarFiltersForm)form, "bandejaDeEntrada", userName, jbpmManager.getInboxQueryString(userName), session);
		ToolbarRegister.registerDefault(request, toolbar);
										
		List<Object[]> tasks = jbpmManager.getTasksNames();
		List<String> process = jbpmManager.getProcessNames();
		
		// llenado de filtros
		request.setAttribute("tareas",collectionHandler.getWorkFlowTasks(tasks));
		request.setAttribute("procesos",collectionHandler.getWorkFlowDefinitions(process));
		
		return mapping.findForward("success");
	}

	/**
	 * Según el id de la tarea seleccionada carga el action de la tarea
	 */
	public ActionForward cargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// grabo un token que tiene que ser validado por cargarTarea de
		// GenericJbpmTaskAction
		this.saveToken(request);

		// logued user name
		String userName = request.getUserPrincipal().getName();
		String strTaskId = request.getParameter(JbpmConstants.WebVariableNames.ID_TASK_INSTANCE);
		// Id de task instance a levantar

		if (strTaskId == "") {
			throw new NullArgumentException("idTaskInstance");
		}

		Long taskId = new Long(strTaskId);
		//ActionForward actionForward = jbpmManager.takeTask(taskId, userName);
		ActionForward actionForward = jbpmManager.getTask(taskId, userName);

		// set current task id
		request.getSession().setAttribute(JbpmConstants.WebVariableNames.CURRENT_TASK, taskId);

		// redirijo al action que me indica el JBPM
		return actionForward;
	}
	
	
	public ActionForward cancelarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String userName = request.getUserPrincipal().getName();
		Long taskId = (Long) request.getSession().getAttribute(JbpmConstants.WebVariableNames.CURRENT_TASK);
		
		if(taskId!=null) jbpmManager.unTakeTask(taskId,userName);
		
		return NavigationManager.getPreviousAction(request);
	}

	/**
	 * Libera la tarea, queda el actor Id vacio
	 */
	public ActionForward liberar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// levanto el nombre del usuario
		Principal userPrincipal = (Principal) request.getUserPrincipal();

		// creo una lista con el ID de la tarea que quiero levantar
		Long taskId = new Long(request.getParameter(JbpmConstants.WebVariableNames.ID_TASK_INSTANCE));

		jbpmManager.unLockTaskInstance(taskId, userPrincipal);

		return NavigationManager.getPreviousAction(request);
	}

	/**
	 * Crea una instancia del proceso indicado en el request
	 */
	public ActionForward nuevaInstanciaProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// levanto el processId seleccionado
		Long processId = new Long(request.getParameter("id"));

		if (processId == null) {
			throw new NullArgumentException("El parametro processId no puede ser nulo");
		}

		// creo nueva instancia
		jbpmManager.newProcessInstanceSettings()
			.setProcessId(processId)
			.createProcessInstance();

		// vuelvo al inventario
		return mapping.findForward("success");
	}

	/**
	 * Muestra en pantalla las tareas pendientes para un usuario
	 */
	@SuppressWarnings("unchecked")
	public ActionForward procesosInstanciados(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// levanto el processId seleccionado
		Long processId = new Long(request.getParameter("id"));

		if (processId == null) {
			throw new NullArgumentException("El parametro processId no puede ser nulo");
		}

		Collection<ProcessInstance> procesosInstanciados = jbpmManager.findProcessInstances(processId);
		request.setAttribute("instancias", procesosInstanciados);

		// vuelvo al inventario
		return mapping.findForward("success");
	}

	/**
	 * Muestra un gráfico con el estado actual del process instance
	 */
	public ActionForward detalleProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// levanto el processId seleccionado
		Long processId = new Long(request.getParameter("id"));

		if (processId == null) {
			throw new NullArgumentException("El parametro processId no puede ser nulo");
		}

		ProcessInstance processInstance = jbpmManager.loadProcessInstance(processId);
		request.setAttribute("processInstance", processInstance);

		// vuelvo al inventario
		return mapping.findForward("success");

	}

	/**
	 * Tareas para process instance
	 */
	public ActionForward tareasProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idWorkFlow = new Long(request.getParameter("id"));
		String userName = WebContextUtil.getRequest().getUserPrincipal().getName();

		Session jbpmSession = JbpmManager.instance().getContext().getSessionFactory().openSession();

		List<TaskInstance> tareasProyecto = jbpmManager.findTaskInstances(userName, idWorkFlow, jbpmSession);
		jbpmSession.close();
		request.setAttribute("tasks", tareasProyecto);
		return mapping.findForward("success");
	}
	
	/**
	 * Cierra la taskinstance asociado al id del request
	 */
	public ActionForward terminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();

		// creo una lista con el ID de la tarea que quiero levantar
		Long taskId = new Long(request.getParameter(JbpmConstants.WebVariableNames.ID_TASK_INSTANCE));

		this.jbpmManager.completeTask(taskId, this.getUser());
		
		// levanto la tarea seleccionada
		//TaskInstance tarea = (TaskInstance) jbpmContext.loadTaskInstanceForUpdate(taskId);
		// la marco como tomada
		//tarea.setActorId(this.getUser());
		//tarea.end();

		return mapping.findForward("success");
	}

	/**
	 * Envía un signal el taskinstance asociado al id del request
	 */
	public ActionForward signal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();

		// creo una lista con el ID de la tarea que quiero levantar
		Long taskId = new Long(request.getParameter(JbpmConstants.WebVariableNames.ID_TASK_INSTANCE));

		// levanto la tarea seleccionada
		TaskInstance tarea = (TaskInstance) jbpmContext.loadTaskInstanceForUpdate(taskId);
		// la marco como tomada
		tarea.getToken().signal();

		return mapping.findForward("success");
	}

	/**
	 * Restaura el esquema de base de datos JBPM
	 */
	public ActionForward borrarBD(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();

		jbpmConfiguration.dropSchema();
		jbpmConfiguration.createSchema();

		return mapping.findForward("success");
	}
	
	protected String getUser(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}
	
	
	public ActionForward actualizarPermisos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		this.service.actualizarPermisosTareas();
		return mapping.findForward("success");
	}
	
	public ActionForward deployProcessDefinition(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		AdjuntoUploadForm upload = (AdjuntoUploadForm)form;
		this.service.deploy(upload);
		request.setAttribute("message", upload.getContent().getFileName() + " deployed");
		return mapping.findForward("success");
	}
}
