package com.fontar.web.action.evaluacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.pragma.web.action.GenericAction;

public class EvaluacionEvaluadorAction extends GenericAction {

	@Override
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	}

	protected void dataCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// String type = (String) request.getParameter(TYPE_PARAMETER);
		// String asignacion =
		// (String)request.getParameter(ASIGNACION_PARAMETER);
		// BeanUtils.setProperty(form,"tipo",type);
		// BeanUtils.setProperty(form,"asignacion",asignacion);
		// request.setAttribute("asignacion", asignacion);
		//		
		// List distribucion = null;
		//	
		// if (type.equals(PARAMETER_JURISDICCION)) {
		//		
		// distribucion = (List)
		// SessionHelper.getDistribucionFinanciamientoJurisdiccion(request);
		// if (distribucion==null) {
		// //TODO Habría q poner la carga del dto en un nivel inferior
		//				
		// JurisdiccionBean jurisdiccionBean;
		// DistribucionFinanciamientoDTO distribucionDTO;
		// distribucion = new ArrayList();
		// //Si no existen jurisdicciones cargadas
		// Collection jurisdiccionList =
		// getServicio().getAll(JurisdiccionBean.class);
		// Iterator iterator = jurisdiccionList.iterator();
		// while(iterator.hasNext()) {
		// jurisdiccionBean = (JurisdiccionBean) iterator.next();
		// distribucionDTO = new DistribucionFinanciamientoDTO();
		// distribucionDTO.setNombre(jurisdiccionBean.getDescripcion());
		// distribucionDTO.setIdJurisdiccion(jurisdiccionBean.getId().toString());
		// distribucion.add(distribucionDTO);
		// }
		// }
		// }
		// else if (type.equals(PARAMETER_REGIONAL)) {
		//		
		// distribucion = (List)
		// SessionHelper.getDistribucionFinanciamientoRegion(request);
		// if (distribucion==null) {
		// //TODO Habría q poner la carga del dto en un nivel inferior
		//				
		// RegionBean regionBean;
		// DistribucionFinanciamientoDTO distribucionDTO;
		// distribucion = new ArrayList();
		// //Si no existen jurisdicciones cargadas
		// Collection jurisdiccionList = getServicio().getAll(RegionBean.class);
		// Iterator iterator = jurisdiccionList.iterator();
		// while(iterator.hasNext()) {
		// regionBean = (RegionBean) iterator.next();
		// distribucionDTO = new DistribucionFinanciamientoDTO();
		// distribucionDTO.setNombre(regionBean.getNombre());
		// distribucionDTO.setIdRegion(regionBean.getId().toString());
		// distribucion.add(distribucionDTO);
		// }
		// }
		// }
		// request.setAttribute(DISTRIBUCION_FINANCIAMIENTO_LIST,distribucion);
	}

}
