package com.fontar.web.action.seguimientos.evaluaciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;


/**
 * 
 * @author ttoth
 * @version 1.01, 19/02/06
 */

public class ObservacionesAction  extends MappingDispatchAction {
		
	protected AdministrarSeguimientoServicio administrarSeguimientoServicio;

	public ActionForward agregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("success");
	}

	public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
//		form.validate(mapping, request);
		String observaciones = BeanUtils.getProperty(form, "observaciones");
		String avance = BeanUtils.getProperty(form, "avance");
		String id = BeanUtils.getProperty(form, "id");
		String esEtapa = BeanUtils.getProperty(form, "esEtapa");

		if (esEtapa.equals("true")) {
			administrarSeguimientoServicio.cargarEtapas(id, avance, observaciones);
		}
		else {
			administrarSeguimientoServicio.cargarActividades(id, avance, observaciones);
		}

		BeanUtils.setProperty(form, "windowClose", "true");
		return mapping.findForward("success");
	}

	public AdministrarSeguimientoServicio getAdministrarSeguimientoServicio() {
		return administrarSeguimientoServicio;
	}

	public void setAdministrarSeguimientoServicio(AdministrarSeguimientoServicio administrarSeguimientoServicio) {
		this.administrarSeguimientoServicio = administrarSeguimientoServicio;
	}
}
