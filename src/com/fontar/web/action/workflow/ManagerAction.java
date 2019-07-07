package com.fontar.web.action.workflow;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;
import org.hibernate.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.fontar.seguridad.AuthenticationService;
import com.pragma.util.ContextUtil;

public class ManagerAction extends MappingDispatchAction {

	/**
	 * Muestra en pantalla los procesos definidos en el esquema
	 */
	public ActionForward procesosDefinidos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();

		Collection procesos = jbpmContext.getGraphSession().findLatestProcessDefinitions();
		request.setAttribute("processDefinitions", procesos);

		return mapping.findForward("success");
	}

	/**
	 * Muestra en pantalla todas las tareas instanciadas del JBPM, con accion de
	 * finalización y signal
	 */
	@SuppressWarnings("unchecked")
	public ActionForward tareas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
		Collection<TaskInstance> tareas;

		Session s = jbpmContext.getSessionFactory().openSession();
		tareas = s.createQuery("from TaskInstance").list();

		request.setAttribute("taskInstances", tareas);
		return mapping.findForward("success");
	}

	/**
	 * Muestra la bandeja de entrada del usuario
	 */
	@SuppressWarnings("unchecked")
	public ActionForward bandeja(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String user = this.getUser();

		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();

		Collection<TaskInstance> tareas = jbpmContext.getTaskMgmtSession().findPooledTaskInstances(user);
		tareas.addAll(jbpmContext.getTaskMgmtSession().findTaskInstances(user));

		request.setAttribute("taskInstances", tareas);

		return mapping.findForward("success");
	}

	/**
	 * Según el id de la tarea seleccionada carga el action de la tarea
	 */
	public ActionForward cargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {


		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
		ActionForward actionForward = new ActionForward();

		// creo una lista con el ID de la tarea que quiero levantar
		Long taskId = new Long(request.getParameter(JbpmConstants.WebVariableNames.ID_TASK_INSTANCE));

		// levanto la tarea seleccionada
		TaskInstance tarea = (TaskInstance) jbpmContext.loadTaskInstanceForUpdate(taskId);
		// la marco como tomada
		if (tarea.getStart() == null) {
			tarea.start();
		}
		tarea.setActorId(this.getUser());

		// levanto el action de struts
		String strutsActionName = (String) tarea.getVariableLocally(JbpmConstants.VariableNames.BEAN_ACTION_NAME);

		actionForward.setName(strutsActionName);
		actionForward.setPath("/" + strutsActionName + ".do");

		// set current task id
		request.getSession().setAttribute(JbpmConstants.WebVariableNames.CURRENT_TASK, tarea.getId());

		// redirijo al action que me indica el JBPM
		return actionForward;
	}

	/**
	 * Cierra la taskinstance asociado al id del request
	 */
	public ActionForward terminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();

		// creo una lista con el ID de la tarea que quiero levantar
		Long taskId = new Long(request.getParameter(JbpmConstants.WebVariableNames.ID_TASK_INSTANCE));

		// levanto la tarea seleccionada
		TaskInstance tarea = (TaskInstance) jbpmContext.loadTaskInstanceForUpdate(taskId);
		// la marco como tomada
		tarea.setActorId(this.getUser());
		tarea.end();

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
	 * Libera la tarea, queda el actor Id vacio
	 */
	public ActionForward liberar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();

		// creo una lista con el ID de la tarea que quiero levantar
		Long taskId = new Long(request.getParameter(JbpmConstants.WebVariableNames.ID_TASK_INSTANCE));

		// levanto la tarea seleccionada
		TaskInstance tarea = (TaskInstance) jbpmContext.loadTaskInstanceForUpdate(taskId);
		tarea.setActorId("");

		return mapping.findForward("success");
	}

	/**
	 * Crea una instancia del proceso indicado en el request
	 */
	public ActionForward nuevaInstanciaProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();

		// levanto el processId seleccionado
		Long processId = new Long(request.getParameter("id"));

		// levanto la definición
		GraphSession graphSession = jbpmContext.getGraphSession();
		ProcessDefinition processDefinition = graphSession.loadProcessDefinition(processId);

		// creo la instancia
		ProcessInstance processInstance = new ProcessInstance(processDefinition);
		// avanzo el token para salir del start-state
		processInstance.getRootToken().signal();

		// guardo la instancia
		jbpmContext.save(processInstance);

		// vuelvo al inventario
		return mapping.findForward("success");
	}

	/**
	 * Muestra en pantalla las tareas pendientes para un usuario
	 */
	@SuppressWarnings("unchecked")
	public ActionForward procesosInstanciados(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();

		// levanto el processId seleccionado
		Long processId = new Long(request.getParameter("id"));

		// levanto la definición
		GraphSession graphSession = jbpmContext.getGraphSession();

		// instancia del proceso
		Collection<ProcessInstance> procesosInstanciados = graphSession.findProcessInstances(processId);

		request.setAttribute("instancias", procesosInstanciados);

		// vuelvo al inventario
		return mapping.findForward("success");

	}

	/**
	 * Muestra un gráfico con el estado actual del process instance
	 */
	public ActionForward detalleProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
		GraphSession graphSession = jbpmContext.getGraphSession();

		// levanto el processId seleccionado
		Long processId = new Long(request.getParameter("id"));

		ProcessInstance processInstance = graphSession.loadProcessInstance(processId);
		request.setAttribute("processInstance", processInstance);

		// vuelvo al inventario
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
	
	public String getUser(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}
}
