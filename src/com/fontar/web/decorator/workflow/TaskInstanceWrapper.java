package com.fontar.web.decorator.workflow;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.bus.api.workflow.WFMiscelaneos;
import com.fontar.data.api.domain.Workflowable;
import com.fontar.jbpm.util.JbpmConstants;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.pragma.util.ContextUtil;

public class TaskInstanceWrapper extends TableDecoratorSupport {

	/**
	 * Link de carga de tarea
	 * @return
	 */
	public String getLinkCargar() {
		TaskInstance taskInstance = (TaskInstance) this.getCurrentRowObject();
		StringBuffer sb = new StringBuffer();

		sb.append("<a href=\"WFCargarTarea.do");
		sb.append("?");
		sb.append(JbpmConstants.WebVariableNames.ID_TASK_INSTANCE);
		sb.append("=");
		sb.append(taskInstance.getId());
		sb.append("\"><img src='images/cargarproyecto.gif' alt='carga la tarea' border=0></a>");

		return sb.toString();
	}

	/**
	 * Link de terminacion de tarea. USAR con CUIDADO!
	 * @return
	 */
	public String getLinkTerminar() {
		TaskInstance taskInstance = (TaskInstance) this.getCurrentRowObject();
		StringBuffer sb = new StringBuffer();

		sb.append("<a href=\"WFTerminarTarea.do");
		sb.append("?");
		sb.append(JbpmConstants.WebVariableNames.ID_TASK_INSTANCE);
		sb.append("=");
		sb.append(taskInstance.getId());
		sb.append("\"><img src='images/controlar.gif' alt='termina la tarea' border=0></a>");

		return sb.toString();
	}

	public String getLinkSignal() {
		TaskInstance taskInstance = (TaskInstance) this.getCurrentRowObject();
		StringBuffer sb = new StringBuffer();

		sb.append("<a href=\"WFSignalToken.do");
		sb.append("?");
		sb.append(JbpmConstants.WebVariableNames.ID_TASK_INSTANCE);
		sb.append("=");
		sb.append(taskInstance.getId());
		sb.append("\"><img src='images/evaluar.gif' alt='signal al token de la tarea' border=0></a>");

		return sb.toString();
	}

	/**
	 * 
	 * @return
	 */
	public String getLinkLiberar() {
		TaskInstance taskInstance = (TaskInstance) this.getCurrentRowObject();
		StringBuffer sb = new StringBuffer();

		if (taskInstance.getActorId() != null && taskInstance.getEnd() == null) {
			sb.append("<a href=\"WFLiberarTarea.do");
			sb.append("?");
			sb.append(JbpmConstants.WebVariableNames.ID_TASK_INSTANCE);
			sb.append("=");
			sb.append(taskInstance.getId());
			sb.append("\"><img src='images/clear_copia.gif' alt='libera la tarea' border=0></a>");
		}

		return sb.toString();
	}

	/**
	 * 
	 * @return
	 */
	public String getLinkDetalle() {
		TaskInstance taskInstance = (TaskInstance) this.getCurrentRowObject();
		StringBuffer sb = new StringBuffer();

		sb.append("<a href=\"WFDetalleInstanciaProceso.do");
		sb.append("?id=");
		sb.append(taskInstance.getToken().getProcessInstance().getId());
		sb.append("\"");
		sb.append("onclick=\"window.open('WFDetalleInstanciaProceso.do");
		sb.append("?id=");
		sb.append(taskInstance.getToken().getProcessInstance().getId());
		sb.append("','window','scrollbars=1,menubar=0,location=0,resizable=1');return false\")");
		sb.append("><img src='images/lupa.gif' border=0></a>");

		return sb.toString();
	}

	/**
	 * Descripción del negocio de FONTAR para poder relacionar la tarea
	 * @return
	 */
	public String getBusinessDescription() {
		TaskInstance taskInstance = (TaskInstance) this.getCurrentRowObject();

		// get idWorkflow from task
		Long idWorkFlow = taskInstance.getToken().getProcessInstance().getId();

		WFMiscelaneos wfMiscelaneos = (WFMiscelaneos) ContextUtil.getBean("wfMiscelaneos");
		Workflowable bean = wfMiscelaneos.obtenerWorkflowable(idWorkFlow);

		String description;
		if (bean == null) {
			description = "";			
		}
		else {
			description = bean.getBusinessDescription(); 
		}
		
		return description;
	}
}



