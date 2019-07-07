package com.fontar.data.impl.domain.bean;


/**
 * Estos objetos representan evaluaciones de un seguimiento del proyecto. 
 */
public class EvaluacionSeguimientoBean extends EvaluacionGeneralBean {
	private SeguimientoBean seguimiento;
	private Long idSeguimiento;

	public Long getIdSeguimiento() {
		return idSeguimiento;
	}
	public void setIdSeguimiento(Long idSeguimiento) {
		this.idSeguimiento = idSeguimiento;
	}
	public SeguimientoBean getSeguimiento() {
		return seguimiento;
	}
	public void setSeguimiento(SeguimientoBean seguimiento) {
		this.seguimiento = seguimiento;
	}
}