package com.fontar.data.impl.domain.bean;

import java.util.Date;

import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
/**
 * Estos objetos se emplean para registrar sucesos especificos en relacion a cada proyecto.
 * Registran, entre otros datos, la fecha, tema y descripcion del evento.
 * Un registro pertenece a un único proyecto, pero adicionalmente puede estar asociado, entre otros, a una evaluacion del proyecto, 
 * a un seguimiento del proyecto, o a un paquete que contiene el proyecto.
 * Existen dos grandes categorias de registros, los registros que genera el sistema en forma autómica
 * y los registros que adiciona en forma manual el usuario. 
 * Para mayor informacion consultar el bean TipoBitacora.  
 * @see com.fontar.data.impl.domain.codes.bitacora.TipoBitacora
 * */
public class BitacoraBean {
		
	private Long id;

	private Long idProyecto;

	private ProyectoRaizBean proyecto;

	private Long idSeguimiento;

	private Long idEvaluacion;

	private String idUsuario = "default";

	private String descripcion;

	private Date fechaAsunto;
	
	private TipoBitacora tipo;

	private Date fechaRegistro;

	private String tema;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaAsunto() {
		return fechaAsunto;
	}

	public void setFechaAsunto(Date fechaAsunto) {
		this.fechaAsunto = fechaAsunto;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEvaluacion() {
		return idEvaluacion;
	}

	public void setIdEvaluacion(Long idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}

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

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}


	public TipoBitacora getTipo() {
		return tipo;
	}

	public void setTipo(TipoBitacora tipo) {
		this.tipo = tipo;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Boolean esManual(){
		return this.tipo.equals(TipoBitacora.MANUAL);
	}

	public Boolean esAdmision(){
		return this.tipo.equals(TipoBitacora.ADMISION);
	}

	public Boolean esEvaluacion(){
		return this.tipo.equals(TipoBitacora.EVALUACION);
	}
	
	public Boolean tieneEvaluacionAsociada(){
		return (this.esEvaluacion() || (this.getIdEvaluacion() != null));
	}
	
	public Boolean tieneSeguimientoAsociado(){
		return (this.esSeguimiento() || (this.getIdSeguimiento() != null));
	}
	
	public Long idEvaluacionAsociada(){
		if  (this.esEvaluacion()) 
			return this.id;
		else
			return this.idEvaluacion;
	}
	
	public Boolean esProyectoPaquete(){
		return this.tipo.equals(TipoBitacora.PROY_PAQUETE);
	}
	
	public Boolean esSeguimiento(){
		return this.tipo.equals(TipoBitacora.SEGUIMIENTO);
	}
	
	public Boolean esPresupuesto(){
		return this.tipo.equals(TipoBitacora.PRESUPUESTO);
	}
	
	public ProyectoRaizBean getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoRaizBean proyecto) {
		this.proyecto = proyecto;
	}

	public boolean esAnalisisDeReconsideracion() {
		return TipoBitacora.RECONSIDERACION.equals(getTipo());
	}
}