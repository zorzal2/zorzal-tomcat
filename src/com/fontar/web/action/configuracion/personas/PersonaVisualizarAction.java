package com.fontar.web.action.configuracion.personas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.data.impl.domain.bean.EspecialidadEvaluadorBean;
import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.pragma.bus.api.GenericABMService;

public class PersonaVisualizarAction extends MappingDispatchAction {

	private ConfiguracionServicio servicio;
	private GenericABMService<EspecialidadEvaluadorBean> servicioEvaluador;
		

	public void setServicio(ConfiguracionServicio servicio) {
		this.servicio = servicio;
	}
	public void setServicioEvaluador(GenericABMService<EspecialidadEvaluadorBean> servicioEvaluador) {
		this.servicioEvaluador = servicioEvaluador;
	}
	
	public ActionForward visualizar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,			
			HttpServletResponse response
		) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		Long id = (Long) dynaForm.get("id");
		
		PersonaDTO persona = servicio.obtenerPersona(id);
		
		if (persona.getEsEvaluador()) {
			persona.setEvaluadorDTO(servicio.obtenerEvaluador(id));
		}
				
		if (persona.getEsEvaluador()) {
			if (servicio.obtenerEvaluador(id).getIdEspecialidadPrimaria()!=null){
			  Long idEsp = servicio.obtenerEvaluador(id).getIdEspecialidadPrimaria();
			  EspecialidadEvaluadorBean especialidadEval = servicioEvaluador.load(idEsp);
		      request.setAttribute("especialidadEval",especialidadEval);
			}
			if (servicio.obtenerEvaluador(id).getIdEspecialidadSecundaria()!=null){
				Long idEspS = servicio.obtenerEvaluador(id).getIdEspecialidadSecundaria();
				EspecialidadEvaluadorBean especialidadEvalSec = servicioEvaluador.load(idEspS);
			    request.setAttribute("especialidadEvalSec",especialidadEvalSec);
			}
		}
	
		request.setAttribute("persona",persona);	
		return mapping.findForward("success");
	}
	
	
}
