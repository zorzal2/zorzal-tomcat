package com.fontar.web.action.administracion;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.dto.ExpedienteMovimientoDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.WebContextUtil;


/**
 * 
 * @author ttoth
 * @version 1.01, 19/02/06
 */

public class ExpedienteMovimientoProyectoAction extends ProyectoBaseAction {
		
	private WFProyectoServicio wfProyectoServicio;
	
	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}

	@Override
	public ActionForward agregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Long idProyecto = null;

		if (validateParameter(request, "id")) {
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		}
		else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);

		setHeaderData(request, visualizacionProyectoDto.getDatosProyectoDto());

		request.setAttribute("submitAction", getSubmitAction());
		
		ExpedienteMovimientoDTO expedienteMovimientoDTO = wfProyectoServicio.obtenerDatosExpedienteMov(idProyecto);
		
		if (expedienteMovimientoDTO != null) {
			BeanUtils.setProperty(form, "observaciones", expedienteMovimientoDTO.getObservacion());
			BeanUtils.setProperty(form, "fecha", DateTimeUtil.formatDate(expedienteMovimientoDTO.getFecha()));
			BeanUtils.setProperty(form, "ubicacion", expedienteMovimientoDTO.getUbicacion());
			BeanUtils.setProperty(form, "idPersonaRepresentante", expedienteMovimientoDTO.getIdPersona());
			setSelectors(form,request);
		}

		return mapping.findForward("success");
	}

	@Override
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Long idProyecto = null;

		if (validateParameter(request, "id")) {
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		}
		else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);

		setHeaderData(request, visualizacionProyectoDto.getDatosProyectoDto());

		request.setAttribute("submitAction", getSubmitAction());
		
		ExpedienteMovimientoDTO expedienteMovimientoDTO = wfProyectoServicio.obtenerDatosExpedienteMov(idProyecto);
		
		if (expedienteMovimientoDTO != null) {
			BeanUtils.setProperty(form, "observaciones", expedienteMovimientoDTO.getObservacion());
			BeanUtils.setProperty(form, "fecha", DateTimeUtil.formatDate(expedienteMovimientoDTO.getFecha()));
			BeanUtils.setProperty(form, "ubicacion", expedienteMovimientoDTO.getUbicacion());
			BeanUtils.setProperty(form, "idPersonaRepresentante", expedienteMovimientoDTO.getIdPersona());
			setSelectors(form,request);
		}
		else {
			
		}
		
		return mapping.findForward("success");
	}

	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Long idProyecto = null;

		if (validateParameter(request, "id")) {
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		}
		else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);

		setHeaderData(request, visualizacionProyectoDto.getDatosProyectoDto());

		request.setAttribute("submitAction", getSubmitAction());
		
		ExpedienteMovimientoDTO expedienteMovimientoDTO = wfProyectoServicio.obtenerDatosExpedienteMov(idProyecto);
		
		if (expedienteMovimientoDTO != null) {
			BeanUtils.setProperty(form, "observaciones", expedienteMovimientoDTO.getObservacion());
			BeanUtils.setProperty(form, "fecha", DateTimeUtil.formatDate(expedienteMovimientoDTO.getFecha()));
			BeanUtils.setProperty(form, "ubicacion", expedienteMovimientoDTO.getUbicacion());
			BeanUtils.setProperty(form, "idPersonaRepresentante", expedienteMovimientoDTO.getIdPersona());
			request.setAttribute("observaciones", expedienteMovimientoDTO.getObservacion());
			request.setAttribute("fecha", DateTimeUtil.formatDate(expedienteMovimientoDTO.getFecha()));
			request.setAttribute("ubicacion", expedienteMovimientoDTO.getUbicacion());
			request.setAttribute("txtPersonaRepresentante", getNombrePersona(form,expedienteMovimientoDTO.getIdPersona()));
			request.setAttribute("idPersonaRepresentante", expedienteMovimientoDTO.getIdPersona());
			setSelectors(form,request);
		}
		else {
			
		}
		
		
		return mapping.findForward("success");
	}

	public ActionForward inventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return 	(this.agregar(mapping,form,request,response));
	}
	
	@Override
	public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		form.validate(mapping, request);

		Long idProyecto = null;

		if (validateParameter(request, "id")) {
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		}
		else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		
		String observaciones = FormUtil.getStringValue(form, "observaciones");
		Date fecha = FormUtil.getDateValue(form, "fecha");
		String ubicacion = FormUtil.getStringValue(form, "ubicacion");
		Date fechaDevolucion = null;
		PragmaDynaValidatorForm test = (PragmaDynaValidatorForm) form;
		if (!test.getDynaClass().getName().equals("ExpedienteProyectoMovimientoDynaForm")) {
			fechaDevolucion = FormUtil.getDateValue(form, "fechaDevolucion");
//			if (fechaDevolucion.before(fecha)) {
//				ActionMessages errors = getErrors(request);
//				addError(errors, "app.configuracion.devolucion.fecha");
//				return mapping.findForward("invalid");
//			}
		}
		Long idPersona = FormUtil.getLongValue(form, "idPersonaRepresentante");

		wfProyectoServicio.guardarMovimiento(fecha,ubicacion,fechaDevolucion,observaciones,idPersona,idProyecto);
		return mapping.findForward("success");
	}

	private void setSelectors(ActionForm form, HttpServletRequest request) throws Exception {
		PersonaDAO personaDAO = (PersonaDAO) WebContextUtil.getBeanFactory().getBean("personaDao");
		String idPersonaRepresentante = BeanUtils.getProperty(form, "idPersonaRepresentante");

		if (idPersonaRepresentante != null && !idPersonaRepresentante.equals("")) {
			PersonaBean personaBean = new PersonaBean();
			personaBean = (PersonaBean) personaDAO.read(new Long(idPersonaRepresentante));
			BeanUtils.copyProperty(form, "idPersonaRepresentante", personaBean.getId());
			BeanUtils.copyProperty(form, "txtPersonaRepresentante", personaBean.getNombre());
		}
	}

	private String getNombrePersona(ActionForm form, Long idPersona) {
		PersonaDAO personaDAO = (PersonaDAO) WebContextUtil.getBeanFactory().getBean("personaDao");

		if (idPersona != null && !idPersona.equals("")) {
			PersonaBean personaBean = new PersonaBean();
			personaBean = (PersonaBean) personaDAO.read(idPersona);
			return personaBean.getNombre();
		}
		return null;
	}

}
