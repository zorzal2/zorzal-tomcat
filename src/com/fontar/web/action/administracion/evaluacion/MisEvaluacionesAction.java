package com.fontar.web.action.administracion.evaluacion;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;


import com.fontar.bus.api.intrumento.AdministrarInstrumentosServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.seguridad.AuthenticationService;
import com.fontar.web.action.IventarioConEstadoAnuladoAction;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;


public abstract class MisEvaluacionesAction extends IventarioConEstadoAnuladoAction {
	
	AdministrarInstrumentosServicio administrarInstrumentosService;
	
	protected String getHqlQueryString() {
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return StringUtil.inject(hqlQueryString(), authenticationService.getUserId());
	}

	
	public void setAdministrarInstrumentosService(AdministrarInstrumentosServicio administrarInstrumentosService) {
		this.administrarInstrumentosService = administrarInstrumentosService;
	}

	@SuppressWarnings("unchecked")
	protected void initInventario(HttpServletRequest request) throws Exception {
		CollectionHandler handler = new CollectionHandler();

		ArrayList instrumentos = new ArrayList();
		instrumentos.addAll(handler.getLabelValueInstrumentos(administrarInstrumentosService.obtenerInstrumentos()));
		request.setAttribute("instrumentos", instrumentos);

		ArrayList estados = (ArrayList) handler.getComboFiltroConEmptyLabelAll(EstadoEvaluacion.class);
		request.setAttribute("estados", estados);
	}

	protected String getPropertyName(){
		return "estado";
	}

	protected abstract String hqlQueryString();
}
