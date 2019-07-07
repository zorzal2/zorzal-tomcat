package com.fontar.data.impl.domain.bean;

import java.util.Set;

/**
 * Los objetos de esta clase representan las diversas estructuras (a modo de templates) para registrar 
 * propiedades técnicas al momento de confeccionar la evaluación técnica del proyecto.<br> 
 * En cada instrumento de beneficio, tanto de Llamados de Convocatoria como de Ventanilla Permanente,  
 * se especifica la matriz de criterios a emplear en la evaluación técnica de cada tipo de proyecto.<br>
 * Estas estructuras son totalmente dinámicas, es decir que al registrar una nueva estructura ya es posible emplearla en el sistema.
 *  
 * @see com.fontar.data.impl.domain.bean.MatrizCriterioItemBean
 */
public class MatrizCriterioBean {

	private Long id;
	private String nombre;
	private Boolean activo;
	
	private Set<MatrizCriterioItemBean> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<MatrizCriterioItemBean> getItems() {
		return items;
	}

	public void setItems(Set<MatrizCriterioItemBean> items) {
		this.items = items;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}