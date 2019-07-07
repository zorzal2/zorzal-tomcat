package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;

public class EvaluacionProyectoPaqueteDTO {
	private String idProyecto;
	private String resultado;
	private String fundamentacion;
	private Long[] idEvaluacionesNoElegibles;
	private Long idpaqueteNoElegible;
	private boolean dictamen;
	private BigDecimal alicuotaSolicitada;
	private BigDecimal alicuotaAdjudicada;

	public String getFundamentacion() {
		return fundamentacion;
	}

	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
	}
	public String getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(String idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Long[] getIdEvaluacionesNoElegibles() {
		return idEvaluacionesNoElegibles;
	}

	public void setIdEvaluacionesNoElegibles(Long[] idEvaluacionesNoElegibles) {
		this.idEvaluacionesNoElegibles = idEvaluacionesNoElegibles;
	}

	public Long getIdpaqueteNoElegible() {
		return idpaqueteNoElegible;
	}

	public void setIdpaqueteNoElegible(Long idpaqueteNoElegible) {
		this.idpaqueteNoElegible = idpaqueteNoElegible;
	}

	public boolean esDictamen() {
		return dictamen;
	}
	public void esDictamen(boolean dictamen) {
		this.dictamen = dictamen;
	}
	public BigDecimal getAlicuotaSolicitada() {
		return alicuotaSolicitada;
	}
	public void setAlicuotaSolicitada(BigDecimal alicuotaSolicitada) {
		this.alicuotaSolicitada = alicuotaSolicitada;
	}

	public BigDecimal getAlicuotaAdjudicada() {
		return alicuotaAdjudicada;
	}

	public void setAlicuotaAdjudicada(BigDecimal alicuotaAdjudicada) {
		this.alicuotaAdjudicada = alicuotaAdjudicada;
	}
}
