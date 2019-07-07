package com.fontar.data.impl.domain.dto;

public class MatrizCriterioItemDTO implements Comparable {
	private long   idItem;
	private String tipoItem;
	private String criterio;
	private String puntaje;
	
	public int compareTo(Object o) {
		MatrizCriterioItemDTO otro = (MatrizCriterioItemDTO)o; 
		
		if(this.idItem > otro.idItem)
			return 1;
		else
			if(this.idItem < otro.idItem)
				return -1;
			else
				return 0;
	}

	public boolean esCriterio() {
		return tipoItem.equals("criterio");
	}
	public boolean esCategoria() {
		return tipoItem.equals("categoria");
	}
	
	public long getIdItem() {
		return idItem;
	}
	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}
	public String getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(String puntaje) {
		this.puntaje = puntaje;
	}
	public String getTipoItem() {
		return tipoItem;
	}
	public void setTipoItem(String tipoItem) {
		this.tipoItem = tipoItem;
	}
	public String getCriterio() {
		return criterio;
	}
	public void setCriterio(String txtItem) {
		this.criterio = txtItem;
	}
}