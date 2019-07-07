package com.fontar.data.impl.domain.bean;

import java.util.Date;

import com.fontar.data.api.domain.Workflowable;
import com.fontar.data.impl.domain.codes.notificacion.EstadoNotificacion;
import com.fontar.data.impl.domain.codes.notificacion.TipoNotificacion;

/**
 * Un objeto de notificación representa información que debe ser comunicada al beneficiario de un proyecto.
 * Estos objetos modelan la notificación, permitiendo registrar el propósito, las fechas asociadas y su estado actual 
 * pero no contiene el contenido real de la notificación.<br>
 * El objetivo es administrar de una manera uniforme aquellas situaciones en las cuales se debe comunicar una situación al beneficiario.   
 * Por este motivo las notificaciones son objetos del negocio que tienen asociado un sencillo circuito de workflow para registrar el envio, el acuse de recibo, etc.
 * Las notificaciones se crean en forma autómatica al realizar distintas tareas de workflow; por ejemplo como 
 * resultado de la tarea de admisión de un proyecto.    
 */
public class NotificacionBean implements Workflowable {

	private Long id;
	private TipoNotificacion tipoNotificacion;
	private String descripcion;
	private Date fechaCreacion;
	private Date fechaEnvio;
	private Date fechaAcuse;
	private Boolean requiereAcuse;
	private EstadoNotificacion estado;
	private Long idWorkFlow;
	private Long idProyecto;
	private String observacion;
	private ProyectoRaizBean proyecto;
	
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
	public Date getFechaAcuse() {
		return fechaAcuse;
	}
	public void setFechaAcuse(Date fechaAcuse) {
		this.fechaAcuse = fechaAcuse;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
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
	public Long getIdWorkFlow() {
		return idWorkFlow;
	}
	public void setIdWorkFlow(Long idWorkFlow) {
		this.idWorkFlow = idWorkFlow;
	}
	public Boolean getRequiereAcuse() {
		return (requiereAcuse == null) ? false:requiereAcuse;
	}
	public void setRequiereAcuse(Boolean requiereAcuse) {
		this.requiereAcuse = requiereAcuse;
	}
	public ProyectoRaizBean getProyecto() {
		return proyecto;
	}
	public void setProyecto(ProyectoRaizBean proyecto) {
		this.proyecto = proyecto;
	}
	public TipoNotificacion getTipoNotificacion() {
		return tipoNotificacion;
	}
	public void setTipoNotificacion(TipoNotificacion tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getBusinessDescription() {
		StringBuffer sb = new StringBuffer();

		sb.append("Nro:");
		sb.append(this.id);
		sb.append(" ");
		
		sb.append("Tipo:");
		sb.append(this.tipoNotificacion);
		sb.append(" ");
		
		sb.append("Estado:");
		sb.append(this.estado.getDescripcion());
		sb.append(" ");

		return sb.toString();
	}
}