package com.fontar.data.impl.domain.bean;

import com.fontar.data.Constant.MatrizPresupuestoTipo;

/**
 * Los objetos de esta clase representan las diversas estructuras (a modo de templates) 
 * para la carga de presupuestos de proyectos.
 * En cada instrumento de beneficio, tanto de Llamados de Convocatoria como de Ventanilla Permanente,  
 * se especifica la matriz de criterios a emplear en los proyectos del instrumento.<br>
 *  Estas estructuras son fijas, es decir que en el caso de registrar una nuevo tipo de presupuesto será necesario 
 *  adaptar la funcionalidad del sistema para que se comporte adecuadamente con la nueva estructura.
 *  
 *  Actualmente, la carga en el sistema del presupuesto de cada proyecto se realiza por medio de una planilla Excel. 
 *  Para cada tipo de presupuesto existe una modelo de planilla diferente que es almacenado 
 *  en la propiedad adjunto del objeto MatrizPresupuestoBean.  
 *  
 *  @see com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean   
 */
public class MatrizPresupuestoBean {

	private Long id;

	private AdjuntoBean adjunto;

	private Long idAdjunto;

	//FIXME: ssanchez - este tipo debería ser un enumerado
	private String tipo;

	private Boolean activo;

	private String nombre;

	public AdjuntoBean getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(AdjuntoBean adjunto) {
		this.adjunto = adjunto;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdAdjunto() {
		return idAdjunto;
	}

	public void setIdAdjunto(Long idAdjunto) {
		this.idAdjunto = idAdjunto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Boolean esTipoCF() {
		return this.getTipo().equals(MatrizPresupuestoTipo.CF);
	}
	
	public Boolean esTipoARAI() {
		return this.getTipo().equals(MatrizPresupuestoTipo.ARAI);
	}

	public Boolean esTipoCFConsejerias() {
		return this.getTipo().equals(MatrizPresupuestoTipo.CF_CONSEJERIAS);
	}
	
	public Boolean esTipoANR() {
		return this.getTipo().equals(MatrizPresupuestoTipo.ANR);
	}
	
	public Boolean esTipoConsejerias() {
		return this.getTipo().equals(MatrizPresupuestoTipo.CONSEJERIAS);
	}

	public Boolean esTipoPatente() {
		return this.getTipo().equals(MatrizPresupuestoTipo.PATENTE);
	}
}