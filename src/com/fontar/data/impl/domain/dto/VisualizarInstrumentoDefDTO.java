package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.displaytag.exception.DecoratorException;

import com.fontar.data.impl.domain.bean.FuenteFinanciamientoBean;
import com.fontar.data.impl.domain.bean.InstrumentoDefBean;
import com.fontar.data.impl.domain.codes.instrumentoDef.TipoInstrumentoDef;
import com.pragma.toolbar.web.decorator.BooleanWrapper;
import com.pragma.util.StringUtil;

public class VisualizarInstrumentoDefDTO {

	
	private InstrumentoDefBean instrumentoDefBean;
	
	private BooleanWrapper booleanWrapper = new BooleanWrapper();

	public VisualizarInstrumentoDefDTO(InstrumentoDefBean instrumentoDefBean) {
		super();
		this.instrumentoDefBean = instrumentoDefBean;
		this.initialize();
	}

	
	//Ejecuta los getters para inicializar las referencias lazy
	private void initialize(){
		this.instrumentoDefBean.getFuenteFinanciamiento().getDenominacion();
		
	}
	public InstrumentoDefBean getInstrumentoDefBean() {
		return instrumentoDefBean;
	}


	public Boolean getActivo() {
		return instrumentoDefBean.getActivo();
	}


	public Set getAdjuntos() {
		return instrumentoDefBean.getAdjuntos();
	}


	public String getCodigoTipo() {
		return instrumentoDefBean.getCodigoTipo();
	}


	public String getDenominacion() {
		return instrumentoDefBean.getDenominacion();
	}


	public Date getFecha() {
		return instrumentoDefBean.getFecha();
	}


	public FuenteFinanciamientoBean getFuenteFinanciamiento() {
		return instrumentoDefBean.getFuenteFinanciamiento();
	}


	public String getGarantia() {
		return instrumentoDefBean.getGarantia();
	}


	public Long getId() {
		return instrumentoDefBean.getId();
	}


	public String getIdentificador() {
		return instrumentoDefBean.getIdentificador();
	}


	public Long getIdFuenteFinanciamiento() {
		return instrumentoDefBean.getIdFuenteFinanciamiento();
	}


	public String getModalidad() {
		return instrumentoDefBean.getModalidad();
	}


	public BigDecimal getMonto() {
		return instrumentoDefBean.getMonto();
	}


	public String getObservacion() {
		return instrumentoDefBean.getObservacion();
	}


	public String getPeriodoGracia() {
		return instrumentoDefBean.getPeriodoGracia();
	}


	public String getPermiteAdjudicacion() {
		return this.getBooleanValue(instrumentoDefBean.getPermiteAdjudicacion());
	}

	public String getPermiteAdquisicion() {
		return this.getBooleanValue(instrumentoDefBean.getPermiteAdquisicion());
	}


	public String getPermiteComision() {
		return  this.getBooleanValue(instrumentoDefBean.getPermiteComision());
	}


	public String getPermiteFinanciamientoBancario() {
		return  this.getBooleanValue(instrumentoDefBean.getPermiteFinanciamientoBancario());
	}


	public String getPermiteMultipleJurisdiccion() {
		return  this.getBooleanValue(instrumentoDefBean.getPermiteMultipleJurisdiccion());
	}


	public String getPermitePropiciado() {
		return  this.getBooleanValue(instrumentoDefBean.getPermitePropiciado());
	}


	public String getPermiteSecretaria() {
		return  this.getBooleanValue(instrumentoDefBean.getPermiteSecretaria());
	}


	public String getPlazoAmortizacion() {
		return instrumentoDefBean.getPlazoAmortizacion();
	}


	public String getPlazoEjecucion() {
		return instrumentoDefBean.getPlazoEjecucion();
	}


	public String getProporcionApoyo() {
		return  StringUtil.formatTwoDecimalForPresentation(instrumentoDefBean.getProporcionApoyo());
	}


	public String getTasaInteres() {
		return instrumentoDefBean.getTasaInteres();
	}


	public TipoInstrumentoDef getTipo() {
		return instrumentoDefBean.getTipo();
	}
	
	
	private String getBooleanValue(Boolean value){
		try {
			return this.booleanWrapper.decorate(value);
		}catch (DecoratorException e) {
			throw new RuntimeException(e);
		}
	}
	
}
