package com.fontar.web.action.seguimientos.evaluaciones;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.evaluacion.EvaluarProyectoServicio;
import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.api.workflow.WFEvaluacionServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.proyecto.presupuesto.plan.ActividadDAO;
import com.fontar.data.api.dao.proyecto.presupuesto.plan.EtapaDAO;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.ActividadBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

public class PlanSeguimientoAction extends EvaluacionSeguimientoBaseAction {

	protected AdministrarSeguimientoServicio administrarSeguimientoServicio;
	protected WFEvaluacionServicio wfEvaluacionServicio;
	protected EvaluarProyectoServicio evaluarProyectoServicio;
	private EvaluacionGeneralDTO evaluacionGeneralDTO;

	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idEvaluacion = getIdEvaluacion(request);
		
		if (idEvaluacion != null) {
			evaluacionGeneralDTO = evaluarProyectoServicio.obtenerEvaluacionGeneral(idEvaluacion);
			request.setAttribute("evaluacion", evaluacionGeneralDTO);
	
			EvaluacionSeguimientoCabeceraDTO cabeceraDTO = evaluarProyectoServicio.obtenerCabeceraEvaluacionSeguimiento(idEvaluacion);
			request.setAttribute(CabeceraAttribute.EVALUACION_SEGUIMIENTO, cabeceraDTO);
			
		}
		else {
			Long idTaskInstance = (Long) request.getSession().getAttribute(JbpmConstants.WebVariableNames.CURRENT_TASK);
		
			evaluacionGeneralDTO = wfEvaluacionServicio.obtenerEvaluacionGeneral(idTaskInstance);
		
			request.setAttribute("evaluacion", evaluacionGeneralDTO);
		
			EvaluacionSeguimientoCabeceraDTO cabeceraDTO = wfEvaluacionServicio.obtenerCabeceraEvaluacionSeguimiento(idTaskInstance);
			request.setAttribute(CabeceraAttribute.EVALUACION_SEGUIMIENTO, cabeceraDTO);
		}
		
		EtapaDAO etapaDAO = (EtapaDAO) ContextUtil.getBean("etapaDao");
		ActividadDAO actividadDAO = (ActividadDAO) ContextUtil.getBean("actividadDao");
		ProyectoDAO proyectoDAO = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyectoBean = proyectoDAO.read(evaluacionGeneralDTO.getIdProyecto());
		Collection tablaEtapa = new LinkedList();
		if (proyectoBean.getIdPresupuesto() != null) {
			List<EtapaBean> etapaList = etapaDAO.findByPresupuesto(proyectoBean.getIdPresupuesto());
			
			if (!etapaList.isEmpty()) {
				Comparator comparator2 = new Comparator(){
					public int compare(Object arg0, Object arg1) {
						EtapaBean etapa1 = (EtapaBean) arg0;
						EtapaBean etapa2 = (EtapaBean) arg1;
						return etapa1.getNombre().compareToIgnoreCase(etapa2.getNombre());
					}};
					
				Collection<EtapaBean> list = new TreeSet(comparator2);
				list.addAll(etapaList);
				for (int i = 0; i < list.size(); i++) {
					EtapaBean etapaBean = (EtapaBean) list.toArray()[i];
					String[] datos = { etapaBean.getNombre(), etapaBean.getDescripcion(), DateTimeUtil.formatDate(etapaBean.getInicio()), DateTimeUtil.formatDate(etapaBean.getFin()), etapaBean.getAvance(), etapaBean.getObservaciones(), "true", etapaBean.getId().toString()};
					tablaEtapa.add(datos);
					Set<ActividadBean> actividades = etapaBean.getActividades();
					Comparator comparator = new Comparator(){
						public int compare(Object arg0, Object arg1) {
							ActividadBean act1 = (ActividadBean) arg0;
							ActividadBean act2 = (ActividadBean) arg1;
							return act1.getNombre().compareToIgnoreCase(act2.getNombre());
						}};
					
					Collection<ActividadBean> act = new TreeSet(comparator);
					act.addAll(actividades);
					for (ActividadBean actividadBean : act) {
						String[] datos2 = {actividadBean.getNombre(), actividadBean.getDescripcion(), DateTimeUtil.formatDate(actividadBean.getInicio()), DateTimeUtil.formatDate(actividadBean.getFin()), actividadBean.getAvance(), actividadBean.getObservacion(), "false", actividadBean.getId().toString()};
						tablaEtapa.add(datos2);
					}
				}
			}
		}
		request.setAttribute("etapas", tablaEtapa);
		BeanUtils.setProperty(form, "id", evaluacionGeneralDTO.getIdProyecto());
		
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public AdministrarSeguimientoServicio getAdministrarSeguimientoServicio() {
		return administrarSeguimientoServicio;
	}

	public void setAdministrarSeguimientoServicio(AdministrarSeguimientoServicio administrarSeguimientoServicio) {
		this.administrarSeguimientoServicio = administrarSeguimientoServicio;
	}

	public EvaluarProyectoServicio getEvaluarProyectoServicio() {
		return evaluarProyectoServicio;
	}

	public void setEvaluarProyectoServicio(EvaluarProyectoServicio evaluarProyectoServicio) {
		this.evaluarProyectoServicio = evaluarProyectoServicio;
	}

	public WFEvaluacionServicio getWfEvaluacionServicio() {
		return wfEvaluacionServicio;
	}

	public void setWfEvaluacionServicio(WFEvaluacionServicio wfEvaluacionServicio) {
		this.wfEvaluacionServicio = wfEvaluacionServicio;
	}

}