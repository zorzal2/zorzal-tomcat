package com.fontar.data.impl.domain.dto.analisisDeGastos;

import java.math.BigDecimal;

/**
 * DTO que contiene cálculos varios que se muestran en las solapas de análisis de gastos.
 * Incluye:
 * <ul>
 * 	<li>Presupuesto según informe de avance</li>
 * 	<li>Monto desembolsado</li>
 * 	<li>Total rendido aprobado</li>
 * 	<li>Total rendido gestionado</li>
 * 	<li>Pendiente de rendición</li>
 * 	<li>A pagar</li>
 * <ul> 
 * 
 * @author llobeto
 *
 */
public class CalculosDeAnalisisDeGastosDTO {
	 private BigDecimal presupuestoSegunInformeDeAvance;
	 private BigDecimal montoDesembolsado;
	 private BigDecimal totalRendidoAprobado;
	 private BigDecimal totalRendidoGestionado;
	 private BigDecimal pendienteDeRendicion;
	 private BigDecimal porPagar;
	 
	 private boolean permiteModificarPresupuestoSegunInformeDeAvance;
	 private boolean permiteModificarPendienteDeRendicion;
	 
	public BigDecimal getMontoDesembolsado() {
		return montoDesembolsado;
	}
	public void setMontoDesembolsado(BigDecimal montoDesembolsado) {
		this.montoDesembolsado = montoDesembolsado;
	}
	public BigDecimal getPendienteDeRendicion() {
		return pendienteDeRendicion;
	}
	public void setPendienteDeRendicion(BigDecimal pendienteDeRendicion) {
		this.pendienteDeRendicion = pendienteDeRendicion;
	}
	public BigDecimal getPresupuestoSegunInformeDeAvance() {
		return presupuestoSegunInformeDeAvance;
	}
	public void setPresupuestoSegunInformeDeAvance(BigDecimal presupuestoSegunInformeDeAvance) {
		this.presupuestoSegunInformeDeAvance = presupuestoSegunInformeDeAvance;
	}
	public BigDecimal getTotalRendidoAprobado() {
		return totalRendidoAprobado;
	}
	public void setTotalRendidoAprobado(BigDecimal totalRendidoAprobado) {
		this.totalRendidoAprobado = totalRendidoAprobado;
	}
	public BigDecimal getTotalRendidoGestionado() {
		return totalRendidoGestionado;
	}
	public void setTotalRendidoGestionado(BigDecimal totalRendidoGestionado) {
		this.totalRendidoGestionado = totalRendidoGestionado;
	}
	public BigDecimal getPorPagar() {
		return porPagar;
	}
	public void setPorPagar(BigDecimal pagar) {
		porPagar = pagar;
	}
	public boolean getPermiteModificarPendienteDeRendicion() {
		return permiteModificarPendienteDeRendicion;
	}
	public void setPermiteModificarPendienteDeRendicion(boolean permiteModificarPendienteDeRendicion) {
		this.permiteModificarPendienteDeRendicion = permiteModificarPendienteDeRendicion;
	}
	public boolean getPermiteModificarPresupuestoSegunInformeDeAvance() {
		return permiteModificarPresupuestoSegunInformeDeAvance;
	}
	public void setPermiteModificarPresupuestoSegunInformeDeAvance(boolean permiteModificarPresupuestoSegunInformeDeAvance) {
		this.permiteModificarPresupuestoSegunInformeDeAvance = permiteModificarPresupuestoSegunInformeDeAvance;
	}	
}