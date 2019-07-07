package com.fontar.web.action.seguimientos.seguimientos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.util.Util;
import com.pragma.util.FormUtil;
import com.pragma.web.PragmaDynaValidatorForm;

/**
 * 
 * @author gboaglio
 * 
 */
public class AdministrarSeguimientoAction extends SeguimientoBaseAction {


	/**
	 * Prepara la pantalla para la edición de un seguimiento
	 * 
	 */
	public ActionForward editar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {
		
		PragmaDynaValidatorForm dyna = (PragmaDynaValidatorForm) form;
		
		Long idProyecto = getIdProyecto(request);
		ProyectoCabeceraDTO dto = administrarSeguimientoServicio.obtenerDatosCabeceraSeguimientoAlta(idProyecto);
		
		// Levanto el id de seguimientos
		Long idSeguimiento = getIdSeguimiento(request);		
		
		String cancelAction = "/SeguimientoVisualizar";
		
		SeguimientoBean seguimiento = administrarSeguimientoServicio.obtenerSeguimiento(idSeguimiento);
		
		// Sólo pueblo el form si no hay errores en el request, lo cual significa que no vengo de 
		// una validación no exitosa
		if (getErrors(request).isEmpty()) {
			
			dyna.set("id",idSeguimiento.toString());
			dyna.set("descripcion",seguimiento.getDescripcion());
			dyna.set("observaciones",seguimiento.getObservacion());
			dyna.set("esFinanciero",seguimiento.getEsFinanciero().toString());
			dyna.set("esTecnico",seguimiento.getEsTecnico().toString());

			String paramVisualizando = request.getParameter("visualizando");
			
			if (Util.isBlank(paramVisualizando) || !paramVisualizando.equals("yes")) {
				cancelAction = "/SeguimientoInventarioFiltrar";
			}
			
		}
		
		if (seguimiento.getRendiciones() != null && seguimiento.getRendiciones().size() > 0) {
			request.setAttribute("tieneRendiciones","true");
		}
		
		request.setAttribute("operacion","editar");
		request.setAttribute(CabeceraAttribute.PROYECTO, dto);
		request.setAttribute("submitAction","/SeguimientoGuardarEdicion");
		request.setAttribute("cancelAction",cancelAction);
		
		return mapping.findForward("success");
	}
	
	/**
	 * Persiste los datos ingresados por el usuario durante la edición de un Seguimiento
	 * @throws Exception 
	 */
	public ActionForward guardarEdicion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception  {
				
		PragmaDynaValidatorForm dyna = (PragmaDynaValidatorForm) form;

		String id				 = (String) dyna.get("id");
		
		Boolean esFinanciero	 = FormUtil.getBooleanValue(form, "_esFinanciero");
		Boolean esTecnico		 = FormUtil.getCheckboxValue(form, "esTecnico");		
		
		String descripcion       = (String) dyna.get("descripcion");
		String observaciones     = (String) dyna.get("observaciones");

		
		administrarSeguimientoServicio.modificarSeguimiento(id, esFinanciero,esTecnico,descripcion,observaciones);
		
		return mapping.findForward("success");
	}
			
}
