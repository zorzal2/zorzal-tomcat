package com.fontar.web.action.administracion;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.bus.api.configuracion.AdministrarMatrizCriteriosServicio;
import com.fontar.bus.api.workflow.WFEvaluacionServicio;
import com.fontar.data.api.dao.DistribucionTipoProyectoDAO;
import com.fontar.data.api.dao.MatrizCriterioDAO;
import com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean;
import com.fontar.data.impl.domain.bean.MatrizCriterioBean;
import com.fontar.data.impl.domain.dto.MatrizCriterioDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.ContextUtil;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.WebContextUtil;


/**
 * 
 * @author ttoth
 * @version 1.01, 19/02/06
 */

public class CargarCriteriosResultadoAction  extends MappingDispatchAction {
		
	protected WFEvaluacionServicio wfEvaluacionServicio;

	private AdministrarMatrizCriteriosServicio administrarMatrizCriteriosServicio;

	public void setAdministrarMatrizCriteriosServicio(AdministrarMatrizCriteriosServicio matrizCriterioServicio) {
		this.administrarMatrizCriteriosServicio = matrizCriterioServicio;
	}

	public void setWfEvaluacionServicio(WFEvaluacionServicio wfEvaluacionServicio) {
		this.wfEvaluacionServicio = wfEvaluacionServicio;
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String id = null;
		String nombre = request.getParameter("nombre");
		
		DistribucionTipoProyectoDAO tipoProyectoDAO = (DistribucionTipoProyectoDAO) ContextUtil.getBean("distribucionTipoProyectoDao");
		if(!StringUtil.isEmpty(nombre)) {
			DistribucionTipoProyectoBean bean = tipoProyectoDAO.read(new Long(nombre));
			id = bean.getIdMatrizCriterio().toString();
		}
		
		MatrizCriterioDAO matrizCriterioDAO = (MatrizCriterioDAO) WebContextUtil.getBeanFactory().getBean("matrizCriterioDao");
		List<MatrizCriterioBean> matrizCriterioList = matrizCriterioDAO.getAll();
		for (MatrizCriterioBean criterioBean : matrizCriterioList) {
			if (criterioBean.getId().toString().equals(id)) {
				BeanUtils.setProperty(form, "criterios", criterioBean.getNombre());	
				MatrizCriterioDTO mc = administrarMatrizCriteriosServicio.obtenerMatrizCriterio(new Long(id));
				request.setAttribute("criterioList", mc.getCriterioList());
			}
		}
		request.setAttribute("nombreCriterio", nombre);
		String proyectoTipoCriterio = (String) SessionHelper.getNombreCriterios(request);
		
		if ((nombre.equals(proyectoTipoCriterio) && (!StringUtil.isEmpty(proyectoTipoCriterio)))) {
			String[] idCriterios = (String[]) SessionHelper.getIdCriterios(request);
			String ids = new String();
			if (idCriterios != null) {
				for (int i = 0; i<idCriterios.length; i++) {
					ids = ids + idCriterios[i];
					ids = ids + ",";
				}
			}
			BeanUtils.setProperty(form, "ids", ids);
		}		
	
		return mapping.findForward("success");
	}

	public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
//		form.validate(mapping, request);
		String[] idItems = FormUtil.getStringArrayValue(form, "elegido");
		String nombre = FormUtil.getStringValue(form, "nombreCriterio");
		SessionHelper.setNombreCriterios(request, nombre);
		SessionHelper.setIdCriterios(request, idItems);
		BeanUtils.setProperty(form, "windowClose", "true");
		return mapping.findForward("success");
	}
}
