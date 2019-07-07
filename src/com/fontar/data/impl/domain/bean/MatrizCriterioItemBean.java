package com.fontar.data.impl.domain.bean;

/**
 * Los objetos de esta clase representan la composición de las diversas estructuras (a modo de templates) empleadadas para registrar 
 * las propiedades técnicas al momento de confeccionar la evaluación técnica del proyecto.<br>
 * Estos objetos, si bien permiten representar estructuras de distinta cantidade de niveles, 
 * actualmente todas las matrices contienen dos niveles de items. 
 * En  el primer nivel se definen los items de criterios, y en el segundo nivel  
 * se definen las distintas opciones (o categorias) asociada con cada criterio. 
 * Cada una de estas categorías tiene asociada un puntaje para permitir calcular al momentos de carga de una evaluación técnica el puntaje técnico asociado con el proyecto.
 *    
 *@see com.fontar.data.impl.domain.bean.MatrizCriterioBean  
 */
public class MatrizCriterioItemBean {
	
	private Long id;
	
	private Long idItemPadre;	
	private MatrizCriterioItemBean itemPadre;
	
	private String denominacion;
	private Long puntaje;	
	
	private Long idMatrizCriterio;	
	private MatrizCriterioBean matrizCriterio;
	
	
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdItemPadre() {
		return idItemPadre;
	}
	public void setIdItemPadre(Long idItemPadre) {
		this.idItemPadre = idItemPadre;
	}
	public Long getIdMatrizCriterio() {
		return idMatrizCriterio;
	}
	public void setIdMatrizCriterio(Long idMatrizCriterio) {
		this.idMatrizCriterio = idMatrizCriterio;
	}
	public Long getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(Long puntaje) {
		this.puntaje = puntaje;
	}
	public MatrizCriterioItemBean getItemPadre() {
		return itemPadre;
	}
	public void setItemPadre(MatrizCriterioItemBean itemPadre) {
		this.itemPadre = itemPadre;
	}
	public MatrizCriterioBean getMatrizCriterio() {
		return matrizCriterio;
	}
	public void setMatrizCriterio(MatrizCriterioBean matrizCriterio) {
		this.matrizCriterio = matrizCriterio;
	}
	
}