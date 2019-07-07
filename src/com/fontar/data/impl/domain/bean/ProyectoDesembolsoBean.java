package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.data.impl.domain.codes.proyecto.desembolso.EstadoProyectoDesembolso;


public class ProyectoDesembolsoBean {

	private Long id;
	
	private String concepto;
	
	private BigDecimal montoOriginal;
	
	private BigDecimal montoAutorizado;

	private BigDecimal montoDesembolsado;
	
	private Integer plazo;
	
	private EstadoProyectoDesembolso codigoEstado;
	
	private Date fechaPago;
	
	private Long idProyecto;
	
	private ProyectoBean proyecto;
	
	private String objetivo;
	
	private Boolean esAnticipo;

	private Long idSeguimientoDeAutorizacion;

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public EstadoProyectoDesembolso getCodigoEstado() {
		return codigoEstado;
	}

	public void setCodigoEstado(EstadoProyectoDesembolso codigoEstado) {
		this.codigoEstado = codigoEstado;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getMontoAutorizado() {
		return montoAutorizado;
	}

	public void setMontoAutorizado(BigDecimal montoAutorizado) {
		this.montoAutorizado = montoAutorizado;
	}

	public BigDecimal getMontoDesembolsado() {
		return montoDesembolsado;
	}

	public void setMontoDesembolsado(BigDecimal montoDesembolsado) {
		this.montoDesembolsado = montoDesembolsado;
	}

	public BigDecimal getMontoOriginal() {
		return montoOriginal;
	}

	public void setMontoOriginal(BigDecimal montoOriginal) {
		this.montoOriginal = montoOriginal;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public Integer getPlazo() {
		return plazo;
	}

	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}

	public ProyectoBean getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoBean proyecto) {
		this.proyecto = proyecto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEsAnticipo() {
		return esAnticipo;
	}

	public void setEsAnticipo(Boolean esAnticipo) {
		this.esAnticipo = esAnticipo;
	}

	public Long getIdSeguimientoDeAutorizacion() {
		return idSeguimientoDeAutorizacion;
	}

	public void setIdSeguimientoDeAutorizacion(Long idSeguimientoDeAutorizacion) {
		this.idSeguimientoDeAutorizacion = idSeguimientoDeAutorizacion;
	}
	
	public Boolean yaFueAutorizado() {
		return 
		this.codigoEstado.equals(EstadoProyectoDesembolso.AUTORIZADO) ||
		this.codigoEstado.equals(EstadoProyectoDesembolso.PAGADO);			
	}

	public Boolean yaFuePagado() {
		return this.codigoEstado.equals(EstadoProyectoDesembolso.PAGADO);			
	}
	/**
	 * Devuelve el monto desembolsado si lo hay, si no el monto
	 * autorizado y si no existe el monto original.
	 * @return
	 */
	public BigDecimal montoVigente() {
		if(this.codigoEstado.equals(EstadoProyectoDesembolso.PAGADO)) 
			return montoDesembolsado;
		if(this.codigoEstado.equals(EstadoProyectoDesembolso.AUTORIZADO)) 
			return montoAutorizado;
		if(this.codigoEstado.equals(EstadoProyectoDesembolso.NO_PAGADO)) 
			return montoOriginal;
		return null;
	}
	
	/**
	 * Devuelve el monto desembolsado si lo hay, si no el monto
	 * autorizado y si no existe el monto original. USADO POR REPORTES EN JASPER!
	 * @return
	 */
	public BigDecimal getMontoVigente() {
		return montoVigente();
	}
	/**
	 * Devuelve el monto desembolsado si lo hay, si no el monto
	 * autorizado y si no existe devuelve null.
	 * @return
	 */
	public BigDecimal montoVigenteAutorizado() {
		if(this.codigoEstado.equals(EstadoProyectoDesembolso.PAGADO)) 
			return montoDesembolsado;
		if(this.codigoEstado.equals(EstadoProyectoDesembolso.AUTORIZADO)) 
			return montoAutorizado;
		return null;
	}
}