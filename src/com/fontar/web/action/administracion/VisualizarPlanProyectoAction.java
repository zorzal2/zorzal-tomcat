package com.fontar.web.action.administracion;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.proyecto.presupuesto.plan.ActividadDAO;
import com.fontar.data.api.dao.proyecto.presupuesto.plan.EtapaDAO;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.ActividadBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

public class VisualizarPlanProyectoAction extends BaseMappingDispatchAction {

	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idProyecto = null;
		
		
		//Long id = new Long(request.getParameter("id"));
		idProyecto = SessionHelper.getIdProyecto(request);
		
		WFProyectoServicio wfProyectoServicio = (WFProyectoServicio) ContextUtil.getBean("wfProyectoService");
		
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		request.setAttribute("ES_ADQUISICION", visualizacionProyectoDto.getPermiteAdquisicion());
		request.setAttribute("tipoInstrumento", visualizacionProyectoDto.getTipoInstrumentoDef());
		
//		AdmisibilidadVisualizarDTO admisibilidadVisualizarDTO = admisibilidadProyectoServicio.getVisualizacionAdmisibilidad(id);
		
//		request.setAttribute("admisibilidad", admisibilidadVisualizarDTO);
		 
		EtapaDAO etapaDAO = (EtapaDAO) ContextUtil.getBean("etapaDao");
		ActividadDAO actividadDAO = (ActividadDAO) ContextUtil.getBean("actividadDao");
		ProyectoDAO proyectoDAO = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyectoBean = proyectoDAO.read(idProyecto);
		Collection tablaEtapa = new LinkedList();
		if (proyectoBean.getProyectoPresupuesto() != null) {
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
					String[] datos = { etapaBean.getNombre(), etapaBean.getDescripcion(), DateTimeUtil.formatDate(etapaBean.getInicio()), DateTimeUtil.formatDate(etapaBean.getFin()), etapaBean.getAvance(), etapaBean.getObservaciones(), "true"};
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
						String[] datos2 = {actividadBean.getNombre(), actividadBean.getDescripcion(), DateTimeUtil.formatDate(actividadBean.getInicio()), DateTimeUtil.formatDate(actividadBean.getFin()), actividadBean.getAvance(), actividadBean.getObservacion(), "false"};
						tablaEtapa.add(datos2);
					}
				}
			}
			request.setAttribute("etapas", tablaEtapa);
		}
		else request.setAttribute("etapas", null);
		
		return mapping.findForward(FORWARD_SUCCESS);
	}

}