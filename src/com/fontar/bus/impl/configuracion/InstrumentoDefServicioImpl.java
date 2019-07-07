package com.fontar.bus.impl.configuracion;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.fontar.bus.api.configuracion.InstrumentoDefServicio;
import com.fontar.data.impl.domain.bean.FuenteFinanciamientoBean;
import com.fontar.data.impl.domain.bean.InstrumentoDefBean;
import com.fontar.data.impl.domain.dto.VisualizarInstrumentoDefDTO;
import com.pragma.bus.impl.GenericABMServiceImpl;

public class InstrumentoDefServicioImpl extends GenericABMServiceImpl<InstrumentoDefBean> implements
		InstrumentoDefServicio {

	// InstrumentoDefDAO instrumentoDefDAO;

	/*
	 * public InstrumentoDefDAO getInstrumentoDefDAO() { return
	 * instrumentoDefDAO; }
	 * 
	 * public void setInstrumentoDefDAO(InstrumentoDefDAO instrumentoDefDAO) {
	 * this.instrumentoDefDAO = instrumentoDefDAO; }
	 */
	public InstrumentoDefServicioImpl(Class<InstrumentoDefBean> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public void store(InstrumentoDefBean bean) {
		FuenteFinanciamientoBean fuente = new FuenteFinanciamientoBean();
		fuente.setIdentificador("ID");
		fuente.setDenominacion("HC DESDE ACTION");
		fuente.setId(new Long(1));
		fuente.setActivo(true);
		bean.setFuenteFinanciamiento(fuente);
		bean.setMonto(BigDecimal.TEN);

		bean.setIdFuenteFinanciamiento(new Long(1));

		dao.create(bean);
	}

	public Collection listaInstrumentosDef() {
		List listaInstrumentos = null;

		listaInstrumentos = this.getAll();
		return listaInstrumentos;
	}

	public VisualizarInstrumentoDefDTO getVisualizarDTO(Long id) {
		InstrumentoDefBean instrumentoDefBean = this.load(id);
		return new VisualizarInstrumentoDefDTO(instrumentoDefBean);
	}

}
