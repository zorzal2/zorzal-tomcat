package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Contiene todos los datos necesarios para mostrar un cuadro con el cronograma de desembolsos de un
 * proyecto o seguimiento.
 * @author llobeto
 *
 */
public class CronogramaDeDesembolsosDTO {

	private List<ProyectoDesembolsoDTO> desembolsos;
	private boolean permiteEditar;
	private boolean permiteAutorizar;
	private boolean permitePagar;
	private boolean permitePagarAnticipo;
	private boolean permiteAgregar;
	private boolean permiteEliminar;
	private BigDecimal montoTotalPrevisto;
	private BigDecimal montoTotalAutorizado;
	private BigDecimal montoTotalDesembolsado;
	private BigDecimal montoPresupuestoSegunAvance;
	private boolean porSeguimiento;
	private Long idSeguimiento;
	private Long idProyecto;
	
	/**
	 * Lista de desembolsos asociados a mostrar.
	 * @return
	 */
	public List<ProyectoDesembolsoDTO> getDesembolsos() {
		return desembolsos;
	}
	/**
	 * Lista de desembolsos asociados a mostrar.
	 * @param desembolsos
	 */
	public void setDesembolsos(List<ProyectoDesembolsoDTO> desembolsos) {
		this.desembolsos = desembolsos;
	}
	/**
	 * Si el cronograma es por proyecto, devuelve null. Si el cronograma es por seguimiento devuelve
	 * el presupuesto segun informe de avance para el seguimiento.
	 * @return
	 */
	public BigDecimal getMontoPresupuestoSegunAvance() {
		return montoPresupuestoSegunAvance;
	}
	public void setMontoPresupuestoSegunAvance(BigDecimal montoPresupuestoSegunAvance) {
		this.montoPresupuestoSegunAvance = montoPresupuestoSegunAvance;
	}
	/**
	 * Suma del total autorizado.
	 * @return
	 */
	public BigDecimal getMontoTotalAutorizado() {
		return montoTotalAutorizado;
	}
	public void setMontoTotalAutorizado(BigDecimal montoTotalAutorizado) {
		this.montoTotalAutorizado = montoTotalAutorizado;
	}
	/**
	 * Suma del total desembolsado.
	 * @return
	 */
	public BigDecimal getMontoTotalDesembolsado() {
		return montoTotalDesembolsado;
	}
	public void setMontoTotalDesembolsado(BigDecimal montoTotalDesembolsado) {
		this.montoTotalDesembolsado = montoTotalDesembolsado;
	}
	/**
	 * Suma del total previsto.
	 * @return
	 */
	public BigDecimal getMontoTotalPrevisto() {
		return montoTotalPrevisto;
	}
	public void setMontoTotalPrevisto(BigDecimal montoTotalPrevisto) {
		this.montoTotalPrevisto = montoTotalPrevisto;
	}
	/**
	 * Devuelve true si la accion de autorización está permitida en este momento.
	 * @return
	 */
	public boolean getPermiteAutorizar() {
		return permiteAutorizar;
	}
	public void setPermiteAutorizar(boolean permiteAutorizar) {
		this.permiteAutorizar = permiteAutorizar;
	}
	/**
	 * Devuelve true si la accion de editar los montos previstos está permitida en este momento.
	 * @return
	 */
	public boolean getPermiteEditar() {
		return permiteEditar;
	}
	public void setPermiteEditar(boolean permiteEditar) {
		this.permiteEditar = permiteEditar;
	}
	/**
	 * Devuelve true si la accion de pagar está permitida en este momento.
	 * @return
	 */
	public boolean getPermitePagar() {
		return permitePagar;
	}
	public void setPermitePagar(boolean permitePagar) {
		this.permitePagar = permitePagar;
	}
	/**
	 * Devuelve true si la accion de pagar anticipos está permitida en este momento.
	 * @return
	 */
	public boolean getPermitePagarAnticipo() {
		return permitePagarAnticipo;
	}
	public void setPermitePagarAnticipo(boolean permitePagarAnticipo) {
		this.permitePagarAnticipo = permitePagarAnticipo;
	}
	/**
	 * Devuelve true si el cuadro corresponde a un seguimiento. Devuelve false si el cuadro corresponde
	 * a todo el proyecto.
	 * @return
	 */
	public boolean getPorSeguimiento() {
		return porSeguimiento;
	}

	/**
	 * Devuelve true si el cuadro corresponde a todo el proyecto. Devuelve false si el cuadro 
	 * corresponde a un seguimiento.
	 * @return
	 */
	public boolean getPorProyecto() {
		return !porSeguimiento;
	}
	public void setPorSeguimiento(boolean porSeguimiento) {
		this.porSeguimiento = porSeguimiento;
	}
	public void setPorProyecto(boolean porProyecto) {
		this.porSeguimiento = !porProyecto;
	}
	/**
	 * Devuelve el id del seguimiento si el cronograma corresponde al de un seguimiento. Si el 
	 * cronograma corresponde a todo el proyecto devuelve null.
	 * @return
	 */
	public Long getIdProyecto() {
		return idProyecto;
	}
	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}
	
	public Long getIdSeguimiento() {
		return idSeguimiento;
	}
	public void setIdSeguimiento(Long idSeguimiento) {
		this.idSeguimiento = idSeguimiento;
	}
	/**
	 * Determina si es valido agregar desembolsos en este momento.
	 * @return
	 */
	public boolean getPermiteAgregar() {
		return permiteAgregar;
	}
	public void setPermiteAgregar(boolean permiteAgregar) {
		this.permiteAgregar = permiteAgregar;
	}
	/**
	 * Determina si es valido eliminar desembolsos en este momento.
	 * @return
	 */
	public boolean getPermiteEliminar() {
		return permiteEliminar;
	}
	public void setPermiteEliminar(boolean permiteEliminar) {
		this.permiteEliminar = permiteEliminar;
	}
}