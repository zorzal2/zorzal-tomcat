package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.data.impl.domain.codes.proyecto.desembolso.EstadoProyectoDesembolso;



public class ProyectoDesembolsoDTO {

	private Long id;
	
	private String concepto;
	
	private BigDecimal montoOriginal;

	private BigDecimal montoAutorizado;
	
	private BigDecimal montoDesembolsado;
		
	private Integer plazo;
	
	private EstadoProyectoDesembolso codigoEstado;
	
	private Date fechaPago;
	
	private Long idProyecto;
	
	private ProyectoDTO proyecto;
	
	private String objetivo;
	
	private Boolean esAnticipo;

	private Long idSeguimientoDeAutorizacion;
	
	//Propiedades contextuales
	private boolean puedeModificarseElMontoPrevisto = false;
	private boolean puedePagarse = false;
	private boolean puedePagarseComoAnticipo = false;
	private boolean puedeEliminarse = false;
	private boolean puedeAutorizarse = false;
	private Long idSeguimientoCorriente;
	
	/**
	 * Devuelve el id del seguimiento contextual si lo hay. Si no hay
	 * ningún seguimiento corriente, devuelve null.
	 * @return
	 */
	public Long getIdSeguimientoCorriente() {
		return idSeguimientoCorriente;
	}

	public void setIdSeguimientoCorriente(Long idSeguimiento) {
		this.idSeguimientoCorriente = idSeguimiento;
	}

	/**
	 * Determina si en la situación para la cual fue creado está
	 * permitido autorizarlo.
	 * @return
	 */
	public boolean getPuedeAutorizarse() {
		return puedeAutorizarse;
	}

	public void setPuedeAutorizarse(boolean puedeAutorizarse) {
		this.puedeAutorizarse = puedeAutorizarse;
	}
	/**
	 * Determina si en la situación para la cual fue creado está permitido
	 * modificar el monto previsto
	 * @return
	 */
	public boolean getPuedeModificarseElMontoPrevisto() {
		return puedeModificarseElMontoPrevisto;
	}

	public void setPuedeModificarseElMontoPrevisto(boolean puedeModificarseElMontoPrevisto) {
		this.puedeModificarseElMontoPrevisto = puedeModificarseElMontoPrevisto;
	}
	/**
	 * Determina si en la situación para la cual fue creado está permitido
	 * pagar este item.
	 * @return
	 */
	public boolean getPuedePagarse() {
		return puedePagarse;
	}
	
	public void setPuedePagarse(boolean puedePagarse) {
		this.puedePagarse = puedePagarse;
	}
	/**
	 * Determina si en la situación para la cual fue creado está permitido
	 * pagar este item como anticipo.
	 * @return
	 */
	public boolean getPuedePagarseComoAnticipo() {
		return puedePagarseComoAnticipo;
	}

	public void setPuedePagarseComoAnticipo(boolean puedePagarseComoAnticipo) {
		this.puedePagarseComoAnticipo = puedePagarseComoAnticipo;
	}
	/**
	 * Determina si en la situación para la cual fue creado está permitido
	 * borrar el item.
	 * @return
	 */
	public boolean getPuedeEliminarse() {
		return puedeEliminarse;
	}

	public void setPuedeEliminarse(boolean puedeEliminarse) {
		this.puedeEliminarse = puedeEliminarse;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
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

	public ProyectoDTO getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoDTO proyecto) {
		this.proyecto = proyecto;
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
}
