package com.fontar.jbpm.manager;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.NullArgumentException;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

public class SeguimientoTaskInstanceManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private TaskInstance currentTaskInstance;
	
	protected static String TASK_ANULAR = "Anular";	
	protected static String SI = "SI";
	protected static String ANULANDO_SEGUIMIENTO = "ANULANDO_SEGUIMIENTO";

	/**
	 * 
	 * @param taskInstanceId
	 */
	public SeguimientoTaskInstanceManager(Long taskInstanceId) {
		this.currentTaskInstance = this.jbpmManager.getTaskInstanceForUpdate(taskInstanceId);
	}
	
	/**
	 * 
	 */
	public SeguimientoTaskInstanceManager() {
	}	

	/**
	 * 
	 * @return
	 */
	public Long getIdSeguimiento() {
		Long idSeguimiento = (Long) currentTaskInstance.getVariable(JbpmConstants.VariableNames.ID_SEGUIMIENTO);
		if (idSeguimiento == null)
			throw new NullArgumentException("El workflow no tiene cargado el atributo "
					+ JbpmConstants.VariableNames.ID_SEGUIMIENTO);
		return idSeguimiento;
	}

	public void finalizarTarea() {
		currentTaskInstance.end();
	}
	
	/**
	 * Ejecuta la acción de workflow de Seugimiento
	 * <code>Anular</code> y lo finaliza.<br>
	 * @param idProcessInstance
	 */
	public void anularSeguimiento(Long idProcessInstance) {
	
		TaskInstance anular = findTaskInstanceByName(this.getUnfinishedTask(idProcessInstance), TASK_ANULAR);
	
		anular.setVariable(ANULANDO_SEGUIMIENTO, SI);
		
		anular.end();
	}
	
	/**
	 * De una coleccion de taskinstance obtiene
	 * la que conincide con el nombre <i>name</i>.<br>
	 * @param tasks
	 * @param name
	 * @return TaskInstance
	 */
	private TaskInstance findTaskInstanceByName(Collection<TaskInstance> tasks,String name)
	{
		TaskInstance task = null;
		
		for(TaskInstance ti: tasks) {
			if (ti.getName().equalsIgnoreCase(name)){
				task = ti;
				return task;
			}
		}
		return task;
	}	
	
	/**
	 * Devuelve una coleccion de taskinstance
	 * abiertas o no finalizadas.<br>
	 * @param idProcessInstance
	 * @return Collection<TaskInstance>
	 */
	private Collection<TaskInstance> getUnfinishedTask(Long idProcessInstance)
	{
		Collection<TaskInstance> allTask = this.getAllTask(idProcessInstance);
		Collection<TaskInstance> unfinishedTask = new ArrayList<TaskInstance>();
		
		for(TaskInstance ti: allTask){
			if (ti.isOpen() && !ti.isCancelled()) {
				unfinishedTask.add(ti);
			}
		}
		return unfinishedTask;
	}
	
	/**
	 * Devuelve todas las tareas, no interesa si estan abiertas
	 * @return Collection<TaskInstance>
	 */
	@SuppressWarnings("unchecked")
	private Collection<TaskInstance> getAllTask(Long idProcessInstance)
	{
		TaskMgmtInstance taskMgmt = jbpmManager.loadProcessInstance(idProcessInstance).getTaskMgmtInstance();
		Collection<TaskInstance> all = new ArrayList<TaskInstance>();
		if (taskMgmt.getTaskInstances()!=null) all.addAll(taskMgmt.getTaskInstances());			
		return all;
	}	
	
}
