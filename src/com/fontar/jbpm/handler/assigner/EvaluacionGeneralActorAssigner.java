package com.fontar.jbpm.handler.assigner;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.bus.api.seguridad.PermissionDescriptor;
import com.fontar.bus.api.seguridad.PermissionDescriptorImpl;
import com.fontar.bus.api.workflow.WFEvaluacionServicio;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.pragma.util.ContextUtil;

public class EvaluacionGeneralActorAssigner extends PerInstanceActorAssigner {

	private static final long serialVersionUID = 1L;

	public String idActionWorkflow;

	@Override
	public String getIdActionWorkflow() {
		return idActionWorkflow;
	}
	
	@Override
	public void setIdActionWorkflow(String idActionWorkflow) {
		this.idActionWorkflow = idActionWorkflow;
	}

	@Override
	protected PermissionDescriptor[] involvedPermissions(TaskInstance taskInstance) {
		WFEvaluacionServicio evaluacionServicio = (WFEvaluacionServicio) ContextUtil.getBean("wfEvaluacionService");
		Long idEvaluacion = evaluacionServicio.obtenerIdEvaluacionGeneral(taskInstance.getId());
		return new PermissionDescriptor[] {
				new PermissionDescriptorImpl(
						EvaluacionGeneralBean.class,
						idEvaluacion,
						idActionWorkflow,
						taskInstance.getToken().getProcessInstance().getId()
				)
		};
	}

}
