package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;

public class DistribucionTipoProyectoDTO {

	private Long id;

	private Long idInstrumento;

	private Long idTipoProyecto;

	private String tipoProyecto;

	private BigDecimal montoTotalAsignado;

	private BigDecimal limiteMaximoProyecto;

	private Long idMatrizCriterio;
	
	private String nombreMatrizCriterio;

	private Integer plazoEjecucion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdInstrumento() {
		return idInstrumento;
	}

	public void setIdInstrumento(Long idInstrumento) {
		this.idInstrumento = idInstrumento;
	}

	public Long getIdTipoProyecto() {
		return idTipoProyecto;
	}

	public void setIdTipoProyecto(Long idTipoProyecto) {
		this.idTipoProyecto = idTipoProyecto;
	}

	public BigDecimal getLimiteMaximoProyecto() {
		return limiteMaximoProyecto;
	}

	public void setLimiteMaximoProyecto(BigDecimal limiteMaximoProyecto) {
		this.limiteMaximoProyecto = limiteMaximoProyecto;
	}

	public BigDecimal getMontoTotalAsignado() {
		return montoTotalAsignado;
	}

	public void setMontoTotalAsignado(BigDecimal montoTotalAsignado) {
		this.montoTotalAsignado = montoTotalAsignado;
	}

	public Integer getPlazoEjecucion() {
		return plazoEjecucion;
	}

	public void setPlazoEjecucion(Integer plazoEjecucion) {
		this.plazoEjecucion = plazoEjecucion;
	}

	public String getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(String tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

	public Long getIdMatrizCriterio() {
		return idMatrizCriterio;
	}

	public void setIdMatrizCriterio(Long idMatrizCriterio) {
		this.idMatrizCriterio = idMatrizCriterio;
	}

	public String getNombreMatrizCriterio() {
		return nombreMatrizCriterio;
	}

	public void setNombreMatrizCriterio(String nombreMatrizCriterio) {
		this.nombreMatrizCriterio = nombreMatrizCriterio;
	}
}
