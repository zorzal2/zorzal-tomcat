package com.fontar.bus.impl.workflow;

import com.fontar.bus.api.workflow.WFProyectoPitecServicio;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.ProyectoPitecBean;
import com.fontar.jbpm.manager.ProyectoPitecProcessManager;
import com.pragma.util.ContextUtil;


public class WFProyectoPitecServicioImpl extends WFProyectoServicioImpl implements WFProyectoPitecServicio {

	@Override
	public void crearInstanciaWF(Long idProyecto, boolean vieneDePresentacion) {
		// Creo un workflow e intercambio id's entre éste y el proyecto que guardé  
		ProyectoPitecProcessManager processManager = new ProyectoPitecProcessManager();
		
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoPitecBean proyecto = (ProyectoPitecBean) proyectoDao.read(idProyecto);
		
		Long idProcessInstance = processManager.nuevoProcessInstance(idProyecto, !vieneDePresentacion, proyecto.getIdInstrumento());
		
		proyecto.setIdWorkFlow(idProcessInstance);
		proyectoDao.update(proyecto);
	}
}


