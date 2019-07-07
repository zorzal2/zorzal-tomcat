package com.fontar.data.impl.domain.bean;

import java.util.Date;

import com.fontar.data.impl.domain.codes.presentacionConvocatoria.EstadoPresentacion;

/**
 * Estos objetos representan las presentación de proyectos en Instrumentos de Benefico 
 * de Llamados de Convocatoria.
 * En una presentación  básicamente se registra la jurisdicción de donde se realizó dicha presentación, 
 * la fecha de la misma, el número asignado (futuro nro. de proyecto) y 
 * el nombre del beneficiario (no la entidad). 
 * Es importante observar que se registra información especifica del proyecto.<br>
 *  
 *  Una presentación puede anularse o modificarse, y cargarse el proyecto en cuyo caso quedará vincualada con su respectivo proyecto.
 *
 *  @see com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean
 *  @see com.fontar.data.impl.domain.bean.ProyectoRaizBean
 */
public class PresentacionConvocatoriaBean {

	private Long id;

	private String codigo;

	private String nombreEntidad;

	private Long idJurisdiccion;

	private Date fechaIngreso;

	private String observaciones;

	private Long idInstrumento;

	private InstrumentoBean instrumento;

	private JurisdiccionBean jurisdiccion;

	private EstadoPresentacion estado;

	public JurisdiccionBean getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(JurisdiccionBean jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
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

	public Long getIdJurisdiccion() {
		return idJurisdiccion;
	}

	public void setIdJurisdiccion(Long idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public boolean estaFinalizada() {
		if (this.estado != null) {
			return this.estado.equals(EstadoPresentacion.FINALIZADA);
		}
		else {
			return false;
		}
	}

	public boolean estaPresentada() {
		if (this.estado != null) {
			return this.estado.equals(EstadoPresentacion.INICIADA);
		}
		else {
			return false;
		}
	}

	/**
	 * Cambios de estado de la entidad
	 * 
	 */
	public void setEstadoFinalizada() {
		this.estado = EstadoPresentacion.FINALIZADA;
	}

	public void setEstadoIniciada() {
		this.estado = EstadoPresentacion.INICIADA;
	}

	public void setEstadoAnulada() {
		this.estado = EstadoPresentacion.ANULADA;
	}

	public InstrumentoBean getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(InstrumentoBean instrumento) {
		this.instrumento = instrumento;
	}

	public EstadoPresentacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoPresentacion estado) {
		this.estado = estado;
	}

	
}
