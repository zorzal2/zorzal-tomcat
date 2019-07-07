package com.fontar.web.action.configuracion.entidades.entidad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.data.impl.domain.dto.EntidadDTO;
import com.pragma.util.FormUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * 
 * @author vmartemianov
 *
 */
public class EntidadVisualizarAction extends BaseMappingDispatchAction {

	private ConfiguracionServicio configuracionService;
	
	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long id = FormUtil.getLongValue(form, "id");
		EntidadDTO entidadDTO = configuracionService.obtenerEntidad(id);
		//Conseguir los datos u dejarlos disonibles en el request
		
		request.setAttribute("entidad", entidadDTO);
		
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ConfiguracionServicio getConfiguracionService() {
		return configuracionService;
	}

	public void setConfiguracionService(ConfiguracionServicio configuracionServicio) {
		this.configuracionService = configuracionServicio;
	}
}