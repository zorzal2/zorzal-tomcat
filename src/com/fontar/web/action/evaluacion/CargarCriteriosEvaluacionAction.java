package com.fontar.web.action.evaluacion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.DistribucionTipoProyectoDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;

public class CargarCriteriosEvaluacionAction extends CargarEvaluacionAction {
	
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}

	@Override
	protected void setCriterios(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response,EvaluacionDTO evaluacion){
		CollectionHandler collectionHandler = new CollectionHandler();
		
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyecto = proyectoDao.read(evaluacion.getIdProyecto());
		DistribucionTipoProyectoDAO tipoProyectoDAO = (DistribucionTipoProyectoDAO) ContextUtil.getBean("distribucionTipoProyectoDao");
		
		Collection<LabelValueBean> tipoProyectoList = new ArrayList<LabelValueBean>();
		try {
			tipoProyectoList.addAll(collectionHandler.getTiposProyecto(tipoProyectoDAO, proyecto));
			request.setAttribute("tiposProyectos", tipoProyectoList);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
}