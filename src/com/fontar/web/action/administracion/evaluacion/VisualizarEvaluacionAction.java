package com.fontar.web.action.administracion.evaluacion;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.configuracion.AdministrarMatrizCriteriosServicio;
import com.fontar.bus.api.evaluacion.AdministrarEvaluacionesServicio;
import com.fontar.bus.api.workflow.WFEvaluacionServicio;
import com.fontar.data.api.dao.DistribucionTipoProyectoDAO;
import com.fontar.data.api.dao.MatrizCriterioDAO;
import com.fontar.data.impl.assembler.VisualizarEvaluacionAssembler;
import com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean;
import com.fontar.data.impl.domain.bean.MatrizCriterioBean;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.MatrizCriterioDTO;
import com.fontar.data.impl.domain.dto.MatrizCriterioItemDTO;
import com.fontar.data.impl.domain.dto.VisualizarEvaluacionGeneralDecorator;
import com.fontar.util.SessionHelper;
import com.fontar.util.Util;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

public class VisualizarEvaluacionAction extends BaseMappingDispatchAction {

	private static final String EVALUACION_ATTRIBUTE = "evaluacion";

	AdministrarEvaluacionesServicio servicio;

	protected WFEvaluacionServicio wfEvaluacionServicio;

	private AdministrarMatrizCriteriosServicio administrarMatrizCriteriosServicio;

	public void setAdministrarMatrizCriteriosServicio(AdministrarMatrizCriteriosServicio matrizCriterioServicio) {
		this.administrarMatrizCriteriosServicio = matrizCriterioServicio;
	}

	public void setWfEvaluacionServicio(WFEvaluacionServicio wfEvaluacionServicio) {
		this.wfEvaluacionServicio = wfEvaluacionServicio;
	}

	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id = String.valueOf(getIdEvaluacion(request));

		if ( !Util.isBlank(id) ){
			EvaluacionDTO dto = this.servicio.getEvaluacionDTO(Long.valueOf(id), new VisualizarEvaluacionAssembler());
			request.setAttribute(EVALUACION_ATTRIBUTE, dto);
			request.setAttribute("tieneEvaluadores",TipoEvaluacion.EVAL_GEN.equals(dto.getTipo()));
			VisualizarCriterios(mapping, form, request,response, id, dto);
			return mapping.findForward( this.findForward( dto ) );
		}
		else
			throw new RuntimeException("El parametro id es requerido");
	}

	private void VisualizarCriterios(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String id, EvaluacionDTO evaluacion) throws IllegalAccessException, InvocationTargetException {

		String idTipoProyecto = null;
		String[] idCriterios = null;
		request.setAttribute("esProyecto",null);
		if (evaluacion.getTipo().getName().equals(TipoEvaluacion.EVAL_GEN.name())) {
			VisualizarEvaluacionGeneralDecorator dto = (VisualizarEvaluacionGeneralDecorator) evaluacion;
			if (dto.getEsTecnica().equals("true")) {
				if (!dto.getProyecto().toString().contains("Idea")) {
					idCriterios = wfEvaluacionServicio.obtenerCriterioEvaluacion(evaluacion.getId());
//					SessionHelper.setIdCriterios(request, idCriterios);
					idTipoProyecto = wfEvaluacionServicio.obtenerTipoProyecto(evaluacion.getEvaluable().getId());
//					BeanUtils.setProperty(form, "idTipoProyecto", idTipoProyecto);
//					SessionHelper.setNombreCriterios(request,idTipoProyecto);		
					request.setAttribute("esProyecto",true);
				}
			}
		}

//		idTipoProyecto = (String) SessionHelper.getNombreCriterios(request);
		DistribucionTipoProyectoDAO tipoProyectoDAO = (DistribucionTipoProyectoDAO) ContextUtil.getBean("distribucionTipoProyectoDao");
		if(!StringUtil.isEmpty(idTipoProyecto)) {
			DistribucionTipoProyectoBean bean = tipoProyectoDAO.read(new Long(idTipoProyecto));
			id = bean.getIdMatrizCriterio().toString();

			Boolean exist = false;
			MatrizCriterioDAO matrizCriterioDAO = (MatrizCriterioDAO) WebContextUtil.getBeanFactory().getBean("matrizCriterioDao");
			List<MatrizCriterioBean> matrizCriterioList = matrizCriterioDAO.getAll();
			for (MatrizCriterioBean criterioBean : matrizCriterioList) {
				if (criterioBean.getId().toString().equals(id)) {
					BeanUtils.setProperty(form, "criterios", criterioBean.getNombre());	
					MatrizCriterioDTO mc = administrarMatrizCriteriosServicio.obtenerMatrizCriterio(new Long(id));
					Collection<MatrizCriterioItemDTO> mcList = new ArrayList<MatrizCriterioItemDTO>();
					for (MatrizCriterioItemDTO dto : mc.getCriterioList()) {
						if(idCriterios != null) {
							for (String idItem : idCriterios) {
								if (idItem.equals(String.valueOf(dto.getIdItem()))) {
									exist = true;
								}
							}
						}
						if (exist) {
							mcList.add(dto);
						}
						exist = false;
					}
					request.setAttribute("criterioList", mcList);
				}
			}
//			request.setAttribute("nombreCriterio", nombre);
//			String proyectoTipoCriterio = (String) SessionHelper.getNombreCriterios(request);
//			
//			if ((nombre.equals(proyectoTipoCriterio) && (!StringUtil.isNullOrBlank(proyectoTipoCriterio)))) {
//				String[] idCriterios = (String[]) SessionHelper.getIdCriterios(request);
//				String ids = new String();
//				if (idCriterios != null) {
//					for (int i = 0; i<idCriterios.length; i++) {
//						ids = ids + idCriterios[i];
//						ids = ids + ",";
//					}
//				}
//				BeanUtils.setProperty(form, "ids", ids);
//			}		
		}
	}

	public AdministrarEvaluacionesServicio getServicio() {
		return servicio;
	}

	public void setServicio(AdministrarEvaluacionesServicio servicio) {
		this.servicio = servicio;
	}
	
	/**
	 * Retorna el string correspondiente al forward indicado para visualiza 
	 * una evaluacion
	 * EvaluacionGeneral -> evaluacionGeneral
	 * Evaluacion -> evaluacion
	 **/
	private String findForward(EvaluacionDTO evaluacionDTO){
		if(evaluacionDTO instanceof VisualizarEvaluacionGeneralDecorator)
			return "evaluacionGeneral";

		return "evaluacion";
	}

	/**
	 * Obtiene el <i>idEvaluacion</i> desde el request
	 * y lo mete en la sesión.<br>
	 * Si el <i>idEvaluacion</i> no esta en el request
	 * lo busca en la sesión.<br>
	 * @param request
	 * @return idEvaluacion
	 */
	protected Long getIdEvaluacion(HttpServletRequest request){
		Long idEvaluacion = null;
		
		if (validateParameter(request,"id")){
			idEvaluacion = new Long(request.getParameter("id"));
			SessionHelper.setIdEvaluacion(request,idEvaluacion);
		} else {
			idEvaluacion = SessionHelper.getIdEvaluacion(request);
		}
		return idEvaluacion;
	}	
}
