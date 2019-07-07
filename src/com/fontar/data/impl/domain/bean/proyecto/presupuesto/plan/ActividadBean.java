package com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan;

import java.util.Date;

import com.pragma.util.StringUtil;
/**
 * Las actividades son las distintas tareas que deben realizarse en una Etapa de un plan de proyecto.
 * 
 * @see com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean
 */
public class ActividadBean {
	public static final int MAX_DESCRIPCION_SIZE = 1000; 
	public static final int MAX_OBSERVACION_SIZE = 1000; 
	public static final int MAX_AVANCE_SIZE = 500; 
	public static final int MAX_NOMBRE_SIZE = 60; 

	private Long id;
	private EtapaBean etapa;
	private String nombre;
	private String descripcion;
	private Date inicio;
	private Date fin;
	private String avance;
	private String observacion;
	
	public String getAvance() {
		return avance;
	}
	public void setAvance(String avance) {
		this.avance = StringUtil.trimToSize(avance, MAX_AVANCE_SIZE);
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = StringUtil.trimToSize(descripcion, MAX_DESCRIPCION_SIZE);
	}
	public EtapaBean getEtapa() {
		return etapa;
	}
	public void setEtapa(EtapaBean etapa) {
		this.etapa = etapa;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = StringUtil.trimToSize(nombre, MAX_NOMBRE_SIZE);
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = StringUtil.trimToSize(observacion, MAX_OBSERVACION_SIZE);
	}
	
	public String toString() {
		return "["+getNombre()+": "+getDescripcion()+" ("+getInicio()+", "+getFin()+")]";
	}
}
