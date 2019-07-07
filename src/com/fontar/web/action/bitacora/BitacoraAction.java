package com.fontar.web.action.bitacora;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.bitacora.AdministrarBitacoraServicio;
import com.fontar.data.impl.domain.dto.BitacoraDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.action.TemplateCargarGuardarAction;

public class BitacoraAction extends TemplateCargarGuardarAction {

	private AdministrarBitacoraServicio administrarBitacoraServicio;

	public void setAdministrarBitacoraServicio(AdministrarBitacoraServicio administrarBitacoraServicio) {
		this.administrarBitacoraServicio = administrarBitacoraServicio;
	}

	@Override
	protected void executeCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {

		// levanto id de session
		Long idProyecto = SessionHelper.getIdProyecto(request);
		BeanUtils.setProperty(form, "idProyecto", idProyecto);

		// veo si viene por parametro
		if (validateParameter(request, "id")) {
			Long idBitacora = new Long(request.getParameter("id"));

			BitacoraDTO bitacora = administrarBitacoraServicio.obtenerBitacora(idBitacora);

			BeanUtils.copyProperty(form, "idBitacora", bitacora.getId());
			BeanUtils.copyProperty(form, "observaciones", bitacora.getDescripcion());
			BeanUtils.copyProperty(form, "fechaAsunto", bitacora.getFechaAsunto());
			BeanUtils.copyProperty(form, "tema", bitacora.getTema());
			
			request.setAttribute("idBitacora", bitacora.getId());
			request.setAttribute("fechaRegistro", bitacora.getFechaRegistro());
		}
		else {
			request.setAttribute("fechaRegistro", DateTimeUtil.getStringFormatDateTime());
		}
	}

	@Override
	protected void executeGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PragmaDynaValidatorForm bitacoraForm = (PragmaDynaValidatorForm) form;

		Long idBitacora = FormUtil.getLongValue(bitacoraForm, "idBitacora");
		Long idProyecto = FormUtil.getLongValue(bitacoraForm, "idProyecto");
		String observaciones = FormUtil.getStringValue(bitacoraForm, "observaciones");
		Date fechaAsunto = FormUtil.getDateValue(bitacoraForm, "fechaAsunto");
		String tema = FormUtil.getStringValue(bitacoraForm, "tema");

		if (idBitacora == null) {
			administrarBitacoraServicio.cargarBitacora(idProyecto, tema, fechaAsunto, observaciones);
		}
		else {
			administrarBitacoraServicio.actualizarBitacora(idBitacora, tema, fechaAsunto, observaciones);
		}

	}
}