package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.notificacion.EstadoNotificacion;
import com.fontar.data.impl.domain.codes.notificacion.TipoNotificacion;

/**
 * Dto de <code>Notificacion.</code>
 * <br>Este dto tiene todos los datos que podría necesitar una <code>Notificacion</code>
 * <br>no sería necesario crear otro para este objeto.<br> 
 * 
 * @author ssanchez
 * 
 */
public class NotificacionDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private Long id;
	private TipoNotificacion tipoNotificacion;
	private String descripcion;
	private String fechaCreacion;
	private String fechaEnvio;
	private String fechaAcuse;
	private Boolean requiereAcuse;
	private EstadoNotificacion estado;
	private Long idProyecto;
	private String observacion;
	private ProyectoRaizDTO proyectoRaiz;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public EstadoNotificacion getEstado() {
		return estado;
	}
	public void setEstado(EstadoNotificacion estado) {
		this.estado = estado;
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
	public Boolean getRequiereAcuse() {
		return requiereAcuse;
	}
	public void setRequiereAcuse(Boolean requiereAcuse) {
		this.requiereAcuse = requiereAcuse;
	}
	public TipoNotificacion getTipoNotificacion() {
		return tipoNotificacion;
	}
	public void setTipoNotificacion(TipoNotificacion tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}
	public ProyectoRaizDTO getProyectoRaiz() {
		return proyectoRaiz;
	}
	public void setProyectoRaiz(ProyectoRaizDTO proyectoRaiz) {
		this.proyectoRaiz = proyectoRaiz;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getFechaAcuse() {
		return fechaAcuse;
	}
	public void setFechaAcuse(String fechaAcuse) {
		this.fechaAcuse = fechaAcuse;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
}