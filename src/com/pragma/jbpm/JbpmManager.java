package com.pragma.jbpm;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.lang.NullArgumentException;
import org.apache.struts.action.ActionForward;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.instantiation.Delegation;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.bus.api.seguridad.PermissionDescriptor;
import com.fontar.bus.api.seguridad.SeguridadObjetoServicio;
import com.fontar.jbpm.handler.assigner.BaseActorAssigner;
import com.fontar.jbpm.util.JbpmConstants;
import com.fontar.seguridad.acegi.FontarGrantedAuthority;
import com.fontar.seguridad.acegi.GrantedPermissionByInstrumento;
import com.fontar.seguridad.acegi.GrantedSimplePermission;
import com.pragma.util.CollectionUtils;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;

/**
 * Manager del JBPM
 * 
 * @author fferrara
 */
public class JbpmManager {
	
	static String PROCESS_INSTANCE_QUERY_STRING = 
			"select s.processInstance.id from StringInstance s "+
			"where s.name = 'PERMISSION'                       "+
			"  and s.value in ( :permissions )                 ";
	
	private static JbpmManager defaultInstance = new JbpmManager();
	public static JbpmManager instance() {return defaultInstance;}
	private JbpmManager() {}
	
	/**
	 * Devuelve el contexto actual de JBPM
	 * 
	 * @return current JbpmContext
	 */
	public JbpmContext getContext() {
		return JbpmContext.getCurrentJbpmContext();
	}

	/**
	 * Devuelve la lista con las definiciones de los últimos procesos deployados
	 * en el JBPM
	 * 
	 * @return Lista con las últimas definiciones
	 */
	public List findLatestProcessDefinitions() {
		List process = this.getContext().getGraphSession().findLatestProcessDefinitions();

		return process;
	}

	/**
	 * Devuelve la lista con las definiciones de los últimos procesos deployados
	 * en el JBPM
	 * 
	 * @return Lista con las últimas definiciones
	 */
	public List findProcessDefinitions() {
		List process = this.getContext().getGraphSession().findAllProcessDefinitions();

		return process;
	}

	/**
	 * Cargar la instancia del proceso indicado
	 * 
	 * @param idProcessInstance
	 * @return ProcessInstance
	 */
	public ProcessInstance loadProcessInstance(Long idProcessInstance) {
		GraphSession graphSession = getContext().getGraphSession();
		ProcessInstance processInstance = graphSession.loadProcessInstance(idProcessInstance);

		return processInstance;
	}

	/**
	 * Devuelve los procesos instanciados para el process id indicado
	 * 
	 * @param processId
	 * @return
	 */
	public List findProcessInstances(Long processId) {
		GraphSession graphSession = getContext().getGraphSession();
		List procesosInstanciados = graphSession.findProcessInstances(processId);

		return procesosInstanciados;
	}

	/**
	 * Devuelve los procesos instanciados para el process name (corresponde a todas las versiones)
	 * 
	 * @param processName
	 * @return
	 */
	public List<ProcessInstance> findProcessInstances(String processName) {
		GraphSession graphSession = getContext().getGraphSession();
		List<ProcessDefinition> procesos = graphSession.findAllProcessDefinitionVersions(processName);
		List<ProcessInstance> procesosInstanciados = new ArrayList<ProcessInstance>();
		for (ProcessDefinition proceso : procesos) {
			procesosInstanciados.addAll(graphSession.findProcessInstances(proceso.getId()));
		}
		return procesosInstanciados;
	}
	
	/**
	 * Devuelve la instancia de la tarea con propositos de modificación
	 * 
	 * @param idTaskInstance
	 * @return
	 */
	public TaskInstance getTaskInstanceForUpdate(Long idTaskInstance) {
		TaskInstance taskInstance = (TaskInstance) getContext().loadTaskInstanceForUpdate(idTaskInstance);

		return taskInstance;
	}

	/**
	 * Devuelve la instancia de la tarea con propositos de modificación
	 * 
	 * @param idTaskInstance
	 * @return
	 */
	public TaskInstance getTaskInstance(Long idTaskInstance) {
		TaskInstance taskInstance = (TaskInstance) getContext().loadTaskInstance(idTaskInstance);

		return taskInstance;
	}

	/**
	 * 
	 * @return List de TaskInstance
	 */
	@SuppressWarnings("unchecked")
	public List findAllTaskInstances() {
		List<TaskInstance> tasks;

		Session s = getContext().getSessionFactory().openSession();

		tasks = s.createQuery("from TaskInstance").list();

		return tasks;
	}

	
	@SuppressWarnings("unchecked")
	public List findOpenTaskInstances() {
		List<TaskInstance> tasks;
		Session s = getContext().getSessionFactory().openSession();
		tasks = s.createQuery("from TaskInstance ti where (ti.isOpen = true or ti.isSuspended=true) and ti.isCancelled=false ").list();
		return tasks;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<TaskInstance> findOpenTaskInstances(Collection<String> permissions) {
		//Nota: se agregó que devuelva tambien las tareas suspendidas porque son tareas que a futuro pueden abrirse. 
		//TODO ver que pasa en el caso de que la subquery retorne vacio, deberia funcionar
		
		Session session = getContext().getSessionFactory().openSession();
		
		StringBuffer openTaskQueryString = new StringBuffer();
		openTaskQueryString.append("select t from org.jbpm.taskmgmt.exe.TaskInstance t ");
		openTaskQueryString.append("where (t.isOpen=true or t.isSuspended=true) and t.isCancelled=false and ");
		openTaskQueryString.append("t.token.processInstance.id in ( ");
		openTaskQueryString.append(PROCESS_INSTANCE_QUERY_STRING);
		openTaskQueryString.append(" ) order by t.id desc ");
		Query openTaskQuery = session.createQuery( openTaskQueryString.toString() );
		openTaskQuery .setParameterList("permissions" , permissions);
		
		return (Collection<TaskInstance>)openTaskQuery.list();
	}
	
	/**
	 * Tareas pendientes para usuario y proceso
	 * 
	 * @param actorId
	 * @param processInstance
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskInstance> findTaskInstances(String actorId, Long processInstance) {
		Session s = getContext().getSessionFactory().openSession();

		return findTaskInstances(actorId, processInstance, s);
	}
	
	/**
	 * Devuelve todas las instancias de tarea abiertas de un processinstance.
	 * @param processInstance
	 * @param s
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskInstance> findTaskInstancesByProcessInstance(Long processInstance, Session s) {
		List<TaskInstance> tasks;

		Query query = s.createQuery("select ti " +
				"from org.jbpm.taskmgmt.exe.TaskInstance ti " +
				"where ti.token.processInstance=:processInstance and ti.isOpen = true");
		query.setLong("processInstance", processInstance);
		tasks = query.list();
		return tasks;
	}

	/**
	 * 
	 * @param idTaskInstance
	 * @param actorId
	 */
	public void lockTaskInstance(Long idTaskInstance, String actorId) {
		if (actorId == null) {
			throw new NullArgumentException("actorId");
		}

		TaskInstance tarea = (TaskInstance) getContext().loadTaskInstanceForUpdate(idTaskInstance);

		// marco la tarea como tomada
		if (tarea.getActorId() == null) {
			tarea.setActorId(actorId);
		}
		else {
			// la tiene tomada el mismo actor?
			if (!tarea.getActorId().equals(actorId)) {
				throw new TakenTaskException(actorId, tarea.getActorId());
			}
		}

		// la marco como tomada
		if (tarea.getStart() == null) {
			tarea.start();
		}
	}

	/**
	 * 
	 * @param idTaskInstance
	 * @param actorId
	 */
	public void unLockTaskInstance(Long idTaskInstance, Principal principal) {
		if (idTaskInstance == null) {
			throw new NullArgumentException("idTaskInstance");
		}

		if (principal == null) {
			throw new NullArgumentException("principal");
		}

		TaskInstance tarea = (TaskInstance) getContext().loadTaskInstanceForUpdate(idTaskInstance);

		// chequeo si es el owner de la tarea
		if (tarea.getActorId() != null) {
			if (tarea.getActorId().equals(principal.getName()))
				tarea.setActorId("");
		}
		else {
			// Si no es el owner solo el ADMIN de WorkFlow puede liberarla
			// if (!principal)
			// {
			// throw new SecurityException("Tiene que tener rol de Administrador
			// de workflow para liberar una tarea de la que no es propietario "
			// + principal.getName());
			// }
		}
	}
	
	public static class ProcessInstanceSettings {
		private String processName = null;
		private Long processId = null;
		private Map<String, Object> contextVariables = new HashMap<String, Object>(5);  
		boolean kickFromStart = true;
		JbpmManager jbpmManager;
		
		protected ProcessInstanceSettings(JbpmManager manager) {
			jbpmManager = manager;
		}
		/**
		 * Especifica el nombre del proceso. Debe especificarse si no 
		 * se especifica un id.
		 * @param name
		 * @return
		 */
		public ProcessInstanceSettings setProcessName(String name) {
			processName = name;
			return this;
		}
		protected String getProcessName() {
			return processName;
		}
		/**
		 * Especifica el id del proceso, debe especificarse si no se
		 * especifica un nombre. Tiene precedencia sobre el nombre.
		 * @param id
		 * @return
		 */
		public ProcessInstanceSettings setProcessId(Long id) {
			processId = id;
			return this;
		}
		protected Long getProcessId() {
			return processId;
		}
		/**
		 * Agrega una variable al contexto de la instancia creada.
		 * @param name
		 * @param value
		 * @return
		 */
		public ProcessInstanceSettings addVariable(String name, Object value) {
			contextVariables.put(name, value);
			return this;
		}
		protected Set<Entry<String, Object>> variables() {
			return contextVariables.entrySet();
		} 
		/**
		 * Determina que al crearse la instancia NO debe avanzarse el token
		 * para salir del estado inicial. Por defecto se avanza el token.
		 * @return
		 */
		public ProcessInstanceSettings dontKickFromStart() {
			kickFromStart = false;
			return this;
		}
		protected boolean shouldKickFromStart() {
			return kickFromStart;
		}
		
		public ProcessInstance createProcessInstance() {
			return jbpmManager.createProcessInstance(this);
		}
	}
	public ProcessInstanceSettings newProcessInstanceSettings() {
		return new ProcessInstanceSettings(this);
	}

	/**
	 * Crea un processInstance con el nombre dado. Asigna las variables de contexto definidas en 
	 * contextVariables y si kickFromStart es true hace avanzar el token para que salga del 
	 * start-state.   
	 * @param processName
	 * @param contextVariables
	 * @param kickFromStart
	 * @return
	 */
	public ProcessInstance createProcessInstance(ProcessInstanceSettings settings) {
		// load process definition
		GraphSession graphSession = getContext().getGraphSession();
		ProcessDefinition processDefinition;
		if(settings.getProcessId()!=null) {
			processDefinition = graphSession.loadProcessDefinition(settings.getProcessId());			
		} else {
			processDefinition = graphSession.findLatestProcessDefinition(settings.getProcessName());
		}

		// create new instance
		ProcessInstance processInstance = new ProcessInstance(processDefinition);

		//Variables de contexto
		for (Entry<String, Object> variable : settings.variables()) {
			processInstance.getContextInstance().setVariable(variable.getKey(), variable.getValue());
		}
		
		if (settings.shouldKickFromStart()) {
			// kick token to exit start-state
			processInstance.getRootToken().signal();
		}

		// save processInstance
		getContext().save(processInstance);

		return processInstance;
	}


	/**
	 * Marca la tarea como tomada por el actor En caso de no estar comenzada la
	 * empieza Si la tarea está tomada por otro actor chilla
	 * 
	 * @param idTaskInstance id de la tarea
	 * @param actorId actor que quiere cargar la tarea
	 * @param actionForward para cargar la tarea
	 */
	public ActionForward takeTask(Long idTaskInstance, String actorId) {
		if (actorId == null) {
			throw new NullArgumentException("actorId");
		}

		// levanto la tarea seleccionada
		TaskInstance tarea = (TaskInstance) getContext().loadTaskInstanceForUpdate(idTaskInstance);

		// marco la tarea como tomada
		if (tarea.getActorId() == null) {
			tarea.setActorId(actorId);
		}
		else {
			// la tiene tomada el mismo actor?
			if (!tarea.getActorId().equals(actorId)) {
				throw new TakenTaskException(actorId, tarea.getActorId());
			}
		}

		// esta empezada? -> la empiezo
		if (tarea.getStart() == null) {
			tarea.start();
		}

		// levanto el action de struts
		String strutsActionName = (String) tarea.getVariableLocally(JbpmConstants.VariableNames.BEAN_ACTION_NAME);

		// Actionforward
		ActionForward actionForward = new ActionForward();
		actionForward.setName(strutsActionName);
		actionForward.setPath("/" + strutsActionName + ".do");

		return actionForward;
	}
	
	public ActionForward getTask(Long idTaskInstance, String actorId) {
		if (actorId == null) {
			throw new NullArgumentException("actorId");
		}

		// levanto la tarea seleccionada
		TaskInstance tarea = (TaskInstance) getContext().loadTaskInstance(idTaskInstance);

		// levanto el action de struts
		String strutsActionName = (String) tarea.getVariableLocally(JbpmConstants.VariableNames.BEAN_ACTION_NAME);

		// Actionforward
		ActionForward actionForward = new ActionForward();
		actionForward.setName(strutsActionName);
		actionForward.setPath("/" + strutsActionName + ".do");

		return actionForward;
	}

	/**
	 * Marca la tarea como tomada por el actor En caso de no estar comenzada la
	 * empieza Si la tarea está tomada por otro actor chilla
	 * 
	 * @param idTaskInstance id de la tarea
	 * @param actorId actor que quiere cargar la tarea
	 * @param actionForward para cargar la tarea
	 */
	public void unTakeTask(Long idTaskInstance, String actorId) {
		if (actorId == null) {
			throw new NullArgumentException("actorId");
		}

		// levanto la tarea seleccionada
		TaskInstance tarea = (TaskInstance) getContext().loadTaskInstanceForUpdate(idTaskInstance);

		// limpio al actor de la tarea
		if (tarea.getActorId() != null && tarea.getActorId().equals(actorId)) {
			tarea.setActorId("");
		}
	}

	/**
	 * Devuelve una lista con las tareas de todos los Workflow definidos, se
	 * utiliza para los filtros de la bandeja de entrada
	 * @return List<Object[]>
	 */
	public List<Object[]> getTasksNames() {
		Session session = getContext().getSession();
		List<Object[]> tasks = session.createQuery("select distinct o.name, o.processDefinition.name from org.jbpm.taskmgmt.def.Task o order by o.processDefinition.name desc").list();
		return tasks;
	}

	/**
	 * Devuelve una lista con los nombres de los procesos definidos en el
	 * workflow
	 * @return List<String>
	 */
	public List<String> getProcessNames() {
		Session session = getContext().getSession();
		List<String> process = session.createQuery("select distinct o.name from org.jbpm.graph.def.ProcessDefinition o").list();
		return process;
	}
	
	/**
	 * Devuelve la query para la bandeja de entrada el usuario, se utiliza para
	 * la Bandeja De Entrada toolbar
	 * @param userName
	 * @return
	 */
	public String getInboxQueryString(String userName){
		//FIXME Ver si esto se puede sacar o es necesaro para abrir la sesion.
		Session session = getContext().getSession();
		
		//Obtengo los permisos del usuario
		List<String> permisosSimples = new ArrayList<String>();
		List<String> permisosPorInstrumento = new ArrayList<String>();
		for(GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			//Solo tomo en consideracion permisos de WF
			if(authority instanceof FontarGrantedAuthority && ((FontarGrantedAuthority)authority).getAuthorityDescription().startsWith("WF")) {
				if(authority instanceof GrantedPermissionByInstrumento) {
					GrantedPermissionByInstrumento fontarAuthority = (GrantedPermissionByInstrumento) authority;
					//Agrego los permisos como una concatenacion de nombre+id instrumento
					permisosPorInstrumento.add(fontarAuthority.getAuthorityDescription()+fontarAuthority.getIdInstrumento().toString());
				} else {
					//Permisos simples
					permisosSimples.add(((GrantedSimplePermission)authority).getAuthorityDescription());
				}
			}
		}
		//Obtengo los permisos por objeto
		List<String> permisosPorObjeto = new ArrayList<String>();
		Collection<PermissionDescriptor> permisosPorObjetoDescriptor = ((SeguridadObjetoServicio)ContextUtil.getBean("seguridadObjetoServicio")).permisosAsignados(userName);
		for(PermissionDescriptor descriptor : permisosPorObjetoDescriptor) {
			//Solo tomo en cuenta permisos de WF
			if(descriptor.getIdProcessInstance()!=null) {
				permisosPorObjeto.add(descriptor.getAccionSobreObjeto()+descriptor.getIdProcessInstance().toString());
			}
		}
		
		//Me aseguro de que no estoy poniendo mas de mil elementos en el in de la query
		//porque no lo tolera el oracle
		if(permisosSimples.size()>1000) permisosSimples = permisosSimples.subList(0, 999);
		if(permisosPorInstrumento.size()>1000) permisosPorInstrumento = permisosPorInstrumento.subList(0, 999);
		if(permisosPorObjeto.size()>1000) permisosPorObjeto = permisosPorObjeto.subList(0, 999);
		
		return 	
			"select distinct o from TaskInstance o "+
//?			"where	(o.isOpen = true or o.isSuspended=true) and o.isCancelled=false "+
			"where	o.isOpen = true and o.isCancelled=false "+
			"	and	( "+
			"			o.variableInstances['PERMISSION'].value in ( '"+ StringUtil.join(permisosSimples, "','") +"' ) "+
			"		or	o.variableInstances['PERMISSION'].value || o.token.processInstance.variableInstances['ID_INSTRUMENTO'].value in ( '"+ StringUtil.join(permisosPorInstrumento, "','") +"' ) "+
			"		or	o.variableInstances['PERMISSION'].value || o.token.processInstance.id in ( '"+ StringUtil.join(permisosPorObjeto, "','") +"' ) "+
			"		) ";
	}

	
	
	/**
	 * Tareas pendientes para usuario y proceso
	 * 
	 * @param actorId
	 * @param processInstance
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskInstance> findTaskInstances(String actorId, Long processInstance, Session s) {
		
		//obtiene el IdInstrumento asociado al processInstance
		Query taskInstancesQuery = s.createQuery(
				 "select v.value from LongInstance v " +
				 " where v.name='ID_INSTRUMENTO' and v.processInstance.id = :processInstance ");
		taskInstancesQuery.setLong("processInstance", processInstance);
		Long idInstrumento = (Long) taskInstancesQuery.uniqueResult();
		  
//		Obtengo los permisos del usuario que aplican al idInstrumento, para el usaurio 
		List<String> permisosSimples = new ArrayList<String>();
		List<String> permisosPorInstrumento = new ArrayList<String>();
		for(GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			//Solo tomo en consideracion permisos de WF
			
			if(authority instanceof FontarGrantedAuthority && ((FontarGrantedAuthority)authority).getAuthorityDescription().startsWith("WF")) {
				if(authority instanceof GrantedPermissionByInstrumento) {
					GrantedPermissionByInstrumento fontarAuthority = (GrantedPermissionByInstrumento) authority;
					if (fontarAuthority.getIdInstrumento().equals(idInstrumento) ) {
						//Agrego los permisos como una concatenacion de nombre+id instrumento
						permisosPorInstrumento.add(fontarAuthority.getAuthorityDescription());
					}
				} else {
					//Permisos simples
					permisosSimples.add(((GrantedSimplePermission)authority).getAuthorityDescription());
				}
			}
		}
		//Obtengo los permisos por objeto relacionados con el processInstance, para el usaurio 
		List<String> permisosPorObjeto = new ArrayList<String>();
		Collection<PermissionDescriptor> permisosPorObjetoDescriptor = ((SeguridadObjetoServicio)ContextUtil.getBean("seguridadObjetoServicio")).permisosAsignados(actorId);
		for(PermissionDescriptor descriptor : permisosPorObjetoDescriptor) {
			//Solo tomo en cuenta permisos de WF
			if(processInstance.equals(descriptor.getIdProcessInstance())) {
				permisosPorObjeto.add(descriptor.getAccionSobreObjeto());
			}
		}
		
		//Me aseguro de que no estoy poniendo mas de mil elementos en el in de la query
		//porque no lo tolera el oracle
		if(permisosSimples.size()>1000) permisosSimples = permisosSimples.subList(0, 999);
		if(permisosPorInstrumento.size()>1000) permisosPorInstrumento = permisosPorInstrumento.subList(0, 999);
		if(permisosPorObjeto.size()>1000) permisosPorObjeto = permisosPorObjeto.subList(0, 999);
		
		taskInstancesQuery = s.createQuery(
				 "select o from TaskInstance o " +
//?				 "where (o.isOpen = true or o.isSuspended=true) and o.isCancelled=false " +
				 "where	o.isOpen = true and o.isCancelled=false "+			 
				 "  and o.token.processInstance = :processInstance " +
				 "	and	( "+
					"			o.variableInstances['PERMISSION'].value in ( '"+ StringUtil.join(permisosSimples, "','") +"' ) "+
					"		or	o.variableInstances['PERMISSION'].value in ( '"+ StringUtil.join(permisosPorInstrumento, "','") +"' ) "+
					"		or	o.variableInstances['PERMISSION'].value in ( '"+ StringUtil.join(permisosPorObjeto, "','") +"' ) "+
					"		) "+
				 "order by o.description"
		);
		taskInstancesQuery.setLong("processInstance", processInstance);
		return  taskInstancesQuery.list();
	}
	
	public void actualizarPermisosTareas(Collection<TaskInstance> taskInstances) {
		for(TaskInstance taskInstance : taskInstances) {
			assignActors(taskInstance);
		}
	}
	
	/**
	 * Actualiza para todas las tareas abiertas el permiso requerido, y para las instancias de Proyectos define la variable ID_INSTRUMENTO para los casos que falta.
	 * Esta funcionalidad es para corregir datos (invocación manual).
	 */
	@SuppressWarnings("unchecked")
	public void actualizarPermisosTareas() {
/*
		//Define la variable ID_INSTRUMENTO para todo instancia de proceso de ProcessDef de PROYECTOS.
		ProyectoDAO proyectoDAO = (ProyectoDAO)ContextUtil.getBean("proyectoDao");
		
		List<ProcessInstance>processInstances = this.findProcessInstances(JbpmConstants.ProcessNames.PROYECTO);
		for (ProcessInstance processInstance : processInstances) {
			ContextInstance contextInstance = processInstance.getContextInstance();
			if (!contextInstance.hasVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO)) {
				if (contextInstance.hasVariable(JbpmConstants.VariableNames.ID_PROYECTO)) {
					Long idProyecto = (Long) contextInstance.getVariable(JbpmConstants.VariableNames.ID_PROYECTO);
					ProyectoBean proyectoBean = (ProyectoBean) proyectoDAO.read(idProyecto);
					Long idInstrumento = proyectoBean.getIdInstrumento();
					contextInstance.setVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, idInstrumento);
				}
			}
		}
*/		
		//Define la variable ID_INSTRUMENTO para todo instancia de proceso de ProcessDef de NOTIFICACIONES (como 0, de IP)..
/*		List<ProcessInstance>processInstances = this.findProcessInstances(JbpmConstants.ProcessNames.NOTIFICACION);
		for (ProcessInstance processInstance : processInstances) {
			ContextInstance contextInstance = processInstance.getContextInstance();
			if (!contextInstance.hasVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO)) {
				contextInstance.setVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, 0L);
			}
		}
	
		//Define la variable ID_INSTRUMENTO para todo instancia de proceso de ProcessDef de EVALUACIONES (como 0, de IP).
		processInstances = this.findProcessInstances(JbpmConstants.ProcessNames.EVALUACION);
		for (ProcessInstance processInstance : processInstances) {
			ContextInstance contextInstance = processInstance.getContextInstance();
			if (!contextInstance.hasVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO)) {
				contextInstance.setVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, 0L);
			}
		}
		*/				
		//Define la variable PERMISSION para cada instancia de tarea segun el permiso defindo en el ProcessDef.
		/*
		List<TaskInstance> taskInstances = this.findOpenTaskInstances();
		for (TaskInstance taskInstance : taskInstances) {
			Delegation delegation = taskInstance.getTask().getAssignmentDelegation();
			if(delegation!=null) {
				String idActionWorkflow = ((ActorAssigner)delegation.getInstance()).getIdActionWorkflow();
				if (taskInstance.hasVariable(JbpmConstants.VariableNames.PERMISSION_REQUIRED)) {
					VariableInstance variable = taskInstance.getVariableInstance(JbpmConstants.VariableNames.PERMISSION_REQUIRED);
					variable.setValue(idActionWorkflow);
				}
				else {
					VariableInstance variable = VariableInstance.create( taskInstance.getToken(), JbpmConstants.VariableNames.PERMISSION_REQUIRED, idActionWorkflow);
					taskInstance.addVariableInstance(variable);
				}
			}
		}	*/
		List<TaskInstance> taskInstances = this.findOpenTaskInstances();
		 
		try {
			for (TaskInstance taskInstance : taskInstances) {
				assignActors(taskInstance);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void assignActors(TaskInstance taskInstance) {
		Delegation delegation = taskInstance.getTask().getAssignmentDelegation();
		if(delegation!=null) {
			BaseActorAssigner baseActorAssigner = ((BaseActorAssigner)delegation.getInstance());
			baseActorAssigner.assign(taskInstance);
		}
	}
	/**
	 * Devuelve una colección con los nombres de los usuarios que pueden realizar
	 * la tarea dada. 
	 * @param taskInstance
	 * @return
	 * @throws Exception
	 */
	private Set<String> capableActors(TaskInstance taskInstance) {
		Delegation delegation = taskInstance.getTask().getAssignmentDelegation();
		if(delegation!=null) {
			BaseActorAssigner baseActorAssigner = ((BaseActorAssigner)delegation.getInstance());
			return CollectionUtils.setWith(baseActorAssigner.getPooledActors(taskInstance));
		} else return new HashSet<String>();
	}

	public void completeTask(Long idTaskInstance, String actorId) {
		// levanto la tarea seleccionada
		TaskInstance tarea = (TaskInstance) getContext().loadTaskInstance(idTaskInstance);
		tarea.end();
	}
	
	
}



