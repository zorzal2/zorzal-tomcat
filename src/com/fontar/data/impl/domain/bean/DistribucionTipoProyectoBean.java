package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;

/**
 * En un instrumento de beneficio, se puede registrar mediante estos objetos
 * información especifica relacionada con cada tipo de proyecto.   
 * Esta informacion comprende: la matriz de presupuesto, el monto máximo por proyecto, 
 * el monto total diponible y plazo máximo de ejecución del proyecto.
 * @see com.fontar.data.impl.domain.bean.InstrumentoBean
 * @see com.fontar.data.impl.domain.bean.TipoProyectoBean  
 */
public class DistribucionTipoProyectoBean {

	private Long id;

	private Long idInstrumento;

	private Long idTipoProyecto;

	private BigDecimal monto;

	private BigDecimal limite;

	private Long idMatrizCriterio;
	
	private MatrizCriterioBean matrizCriterio;

	private Integer plazoEjecucion;

	private TipoProyectoBean tipoProyecto;

	public TipoProyectoBean getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(TipoProyectoBean tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

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

	public BigDecimal getLimite() {
		return limite;
	}

	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Integer getPlazoEjecucion() {
		return plazoEjecucion;
	}

	public void setPlazoEjecucion(Integer plazoEjecucion) {
		this.plazoEjecucion = plazoEjecucion;
	}

	public Long getIdMatrizCriterio() {
		return idMatrizCriterio;
	}

	public void setIdMatrizCriterio(Long idMatrizCriterio) {
		this.idMatrizCriterio = idMatrizCriterio;
	}

	public MatrizCriterioBean getMatrizCriterio() {
		return matrizCriterio;
	}

	public void setMatrizCriterio(MatrizCriterioBean matrizCriterio) {
		this.matrizCriterio = matrizCriterio;
	}
}
