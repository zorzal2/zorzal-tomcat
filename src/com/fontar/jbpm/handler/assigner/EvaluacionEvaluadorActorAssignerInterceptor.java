package com.fontar.jbpm.handler.assigner;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.seguridad.EvaluacionEvaluadorSecurityConfigInterceptor;
import com.fontar.seguridad.EvaluacionEvaluadorUpdateEvent;
import com.pragma.jbpm.JbpmManager;
/**
 * Reasigna los pooled actors ante un evento de cambio sobre la vinculacion
 * de evaluadores con evaluaciones.
 * 
 * @author llobeto
 *
 */
public class EvaluacionEvaluadorActorAssignerInterceptor extends BaseActorAssignerInterceptor implements EvaluacionEvaluadorSecurityConfigInterceptor {
	
	private final String TASK_CARGAR_RESULTADO = "Cargar resultado";
	
	private JbpmManager jbpmManager = JbpmManager.instance();

	public void update(EvaluacionEvaluadorUpdateEvent updateEvent) {
		Session s = jbpmManager.getContext().getSessionFactory().openSession();
		Transaction transaction = s.beginTransaction();
		Collection<TaskInstance> taskInstances = jbpmManager.findTaskInstancesByProcessInstance(updateEvent.getEvaluacion().getIdWorkFlow(), s);
		Collection<TaskInstance> filteredTaskInstances = new ArrayList<TaskInstance>(); 
		for(TaskInstance taskInstance: taskInstances) {
			if(taskInstance.getName().equalsIgnoreCase(TASK_CARGAR_RESULTADO)) {
				filteredTaskInstances.add(taskInstance);
			}
		}
		assign(filteredTaskInstances);
		transaction.commit();
		s.close();
	}
}
