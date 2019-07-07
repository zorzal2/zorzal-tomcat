package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;

import com.pragma.util.Utils;


/**
 * Dto con datos de totales de rendiciones
 * de un seguimiento.<br>
 * @author llobeto
 */
public class ResumenDeRendicionCompactoDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private Long idSeguimiento;
	private BigDecimal totalMontoParteSolicitado;
	private BigDecimal totalMontoContraparteSolicitado;
	private BigDecimal totalMontoTotalSolicitado;

	private BigDecimal totalMontoParteAprobado;
	private BigDecimal totalMontoContraparteAprobado;
	private BigDecimal totalMontoTotalAprobado;
	
	private BigDecimal totalMontoParteGestionado;
	private BigDecimal totalMontoContraparteGestionado;
	private BigDecimal totalMontoTotalGestionado;
	
	private boolean noHayContextoDeEncriptacion = false;
	
	public ResumenDeRendicionCompactoDTO(
			BigDecimal montoParteRendicion,
			BigDecimal montoContraparteRendicion,
			BigDecimal montoParteEvaluacion,
			BigDecimal montoContraparteEvaluacion,
			BigDecimal montoParteGestion,
			BigDecimal montoContraparteGestion
	) {
		totalMontoParteSolicitado = montoParteRendicion;
		totalMontoContraparteSolicitado = montoContraparteRendicion;
		totalMontoTotalSolicitado = Utils.nvl(totalMontoParteSolicitado, BigDecimal.ZERO).add(Utils.nvl(totalMontoContraparteSolicitado, BigDecimal.ZERO));
		
		totalMontoParteAprobado = montoParteEvaluacion;
		totalMontoContraparteAprobado = montoContraparteEvaluacion;
		totalMontoTotalAprobado = Utils.nvl(totalMontoParteAprobado, BigDecimal.ZERO).add(Utils.nvl(totalMontoContraparteAprobado, BigDecimal.ZERO));
		
		totalMontoParteGestionado = montoParteGestion;
		totalMontoContraparteGestionado = montoContraparteGestion;
		totalMontoTotalGestionado = Utils.nvl(totalMontoParteGestionado, BigDecimal.ZERO).add(Utils.nvl(totalMontoContraparteGestionado, BigDecimal.ZERO));
	}
	
	public ResumenDeRendicionCompactoDTO(
			BigDecimal montoParteRendicion,
			BigDecimal montoContraparteRendicion,
			BigDecimal montoTotalRendicion,
			BigDecimal montoParteEvaluacion,
			BigDecimal montoContraparteEvaluacion,
			BigDecimal montoTotalEvaluacion,
			BigDecimal montoParteGestion,
			BigDecimal montoContraparteGestion,
			BigDecimal montoTotalGestion
	) {
		totalMontoParteSolicitado = montoParteRendicion;
		totalMontoContraparteSolicitado = montoContraparteRendicion;
		totalMontoTotalSolicitado = montoTotalRendicion;
		
		totalMontoParteAprobado = montoParteEvaluacion;
		totalMontoContraparteAprobado = montoContraparteEvaluacion;
		totalMontoTotalAprobado = montoTotalEvaluacion;
		
		totalMontoParteGestionado = montoParteGestion;
		totalMontoContraparteGestionado = montoContraparteGestion;
		totalMontoTotalGestionado = montoTotalGestion;
	}
	/**
	 * Devuelve true si no han podido obtenerse los montos porque el usuario no tiene
	 * contexto de encriptacion.
	 * @return
	 */
	public boolean getNoHayContextoDeEncriptacion() {
		return noHayContextoDeEncriptacion;
	}
	
	public void noHayContextoDeEncriptacion() {
		this.noHayContextoDeEncriptacion = true;
	}
	public Long getIdSeguimiento() {
		return idSeguimiento;
	}
	public void setIdSeguimiento(Long idSeguimiento) {
		this.idSeguimiento = idSeguimiento;
	}
	public BigDecimal getTotalMontoParteAprobado() {
		return totalMontoParteAprobado;
	}
	public void setTotalMontoParteAprobado(BigDecimal totalMontoParteAprobado) {
		this.totalMontoParteAprobado = totalMontoParteAprobado;
	}
	public BigDecimal getTotalMontoParteGestionado() {
		return totalMontoParteGestionado;
	}
	public void setTotalMontoParteGestionado(BigDecimal totalMontoParteGestionado) {
		this.totalMontoParteGestionado = totalMontoParteGestionado;
	}
	public BigDecimal getTotalMontoContraparteAprobado() {
		return totalMontoContraparteAprobado;
	}
	public void setTotalMontoContraparteAprobado(BigDecimal totalMontoContraparteAprobado) {
		this.totalMontoContraparteAprobado = totalMontoContraparteAprobado;
	}
	public BigDecimal getTotalMontoContraparteGestionado() {
		return totalMontoContraparteGestionado;
	}
	public void setTotalMontoContraparteGestionado(BigDecimal totalMontoContraparteGestionado) {
		this.totalMontoContraparteGestionado = totalMontoContraparteGestionado;
	}
	public BigDecimal getTotalMontoTotalAprobado() {
		return totalMontoTotalAprobado;
	}
	public void setTotalMontoTotalAprobado(BigDecimal totalMontoTotalAprobado) {
		this.totalMontoTotalAprobado = totalMontoTotalAprobado;
	}
	public BigDecimal getTotalMontoTotalGestionado() {
		return totalMontoTotalGestionado;
	}
	public void setTotalMontoTotalGestionado(BigDecimal totalMontoTotalGestionado) {
		this.totalMontoTotalGestionado = totalMontoTotalGestionado;
	}
	public BigDecimal getTotalMontoContraparteSolicitado() {
		return totalMontoContraparteSolicitado;
	}
	public void setTotalMontoContraparteSolicitado(BigDecimal totalMontoContraparteSolicitado) {
		this.totalMontoContraparteSolicitado = totalMontoContraparteSolicitado;
	}
	public BigDecimal getTotalMontoParteSolicitado() {
		return totalMontoParteSolicitado;
	}
	public void setTotalMontoParteSolicitado(BigDecimal totalMontoParteSolicitado) {
		this.totalMontoParteSolicitado = totalMontoParteSolicitado;
	}
	public BigDecimal getTotalMontoTotalSolicitado() {
		return totalMontoTotalSolicitado;
	}
	public void setTotalMontoTotalSolicitado(BigDecimal totalMontoTotalSolicitado) {
		this.totalMontoTotalSolicitado = totalMontoTotalSolicitado;
	}
}