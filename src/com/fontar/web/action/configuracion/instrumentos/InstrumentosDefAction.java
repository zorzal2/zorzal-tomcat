package com.fontar.web.action.configuracion.instrumentos;

import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.bus.api.configuracion.InstrumentoDefServicio;

/**
 * 
 * @author fferrara
 * @deprecated
 */
public class InstrumentosDefAction extends MappingDispatchAction {

	private InstrumentoDefServicio instrumentoDefServicio;

	public void setInstrumentoDefServicio(InstrumentoDefServicio instrumentoDefServicio) {
		this.instrumentoDefServicio = instrumentoDefServicio;
	}
}