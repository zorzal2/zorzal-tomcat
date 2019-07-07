package com.fontar.bus.impl.workflow;

import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.IdeaProyectoPitecBean;
import com.fontar.jbpm.manager.IdeaProyectoPitecProcessManager;
import com.pragma.util.ContextUtil;


public class WFIdeaProyectoPitecServicioImpl extends WFProyectoServicioImpl {

	@Override
	public void crearInstanciaWF(Long idIdeaProyecto, boolean vieneDePresentacion) {
		// Creo un workflow e intercambio id's entre éste y el proyecto que guardé  
		IdeaProyectoPitecProcessManager processManager = new IdeaProyectoPitecProcessManager();
		
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		IdeaProyectoPitecBean ideaProyectoPitec = (IdeaProyectoPitecBean) proyectoDao.read(idIdeaProyecto);
		
		Long idProcessInstance = processManager.nuevoProcessInstance(idIdeaProyecto, !vieneDePresentacion, ideaProyectoPitec.getIdInstrumento());
		
		ideaProyectoPitec.setIdWorkFlow(idProcessInstance);
		proyectoDao.update(ideaProyectoPitec);
	}
}


