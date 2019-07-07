package com.fontar.web.action.seguimientos.seguimientos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFSeguimientoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.pragma.util.FormUtil;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * 
 * @author gboaglio
 */
public class CargarSeguimientoAction extends GenericJbpmTaskAction {

	private WFSeguimientoServicio wfSeguimientoServicio;	

	/**
	 * Prepara la pantalla para agregar un seguimiento (la de las solapas)
	 */
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);

		//	Cargo datos para la cabecera y acciones
		ProyectoCabeceraDTO dto = wfSeguimientoServicio.obtenerCabeceraAltaSeguimiento(idTaskInstance);		
		request.setAttribute(CabeceraAttribute.PROYECTO, dto);
		
		request.setAttribute("submitAction","/CargarSeguimientoGuardar");
		request.setAttribute("cancelAction","/ProyectoInventarioFiltrar");

	}

	
	/**
	 * Persiste los datos ingresados por el usuario.
	 */
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		PragmaDynaValidatorForm dyna = (PragmaDynaValidatorForm) form;
			
		Boolean esFinanciero	 = FormUtil.getBooleanValue(form, "_esFinanciero");
		
		Boolean esTecnico		 = FormUtil.getCheckboxValue(form,"esTecnico");		
		String descripcion       = (String) dyna.get("descripcion");
		String observaciones     = (String) dyna.get("observaciones");

		wfSeguimientoServicio.cargarSeguimiento(idTaskInstance, esFinanciero, esTecnico, descripcion, observaciones);
		
	}
	
	
	/** Getters y Setters **/
	
	public WFSeguimientoServicio getWfSeguimientoServicio() {
		return wfSeguimientoServicio;
	}

	public void setWfSeguimientoServicio(WFSeguimientoServicio wfSeguimientoServicio) {
		this.wfSeguimientoServicio = wfSeguimientoServicio;
	}

}
