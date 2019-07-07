/**
 * 
 */
package com.fontar.web.action.presupuesto;

import java.util.Collection;

import com.fontar.web.decorator.proyectos.presupuesto.PresupuestoItemWrapper;

public class PresupuestoRubroTabla {
	String titulo;
	Collection<PresupuestoItemWrapper> items;
	Collection<Campo> campos;
	int order;
	public PresupuestoRubroTabla(String titulo, int order) {
		this.titulo = titulo;
		this.order = order;
	}
	public Collection<Campo> getCampos() {
		return campos;
	}
	public void setCampos(Collection<Campo> campos) {
		this.campos = campos;
	}
	public Collection<PresupuestoItemWrapper> getItems() {
		return items;
	}
	public void setItems(Collection<PresupuestoItemWrapper> items) {
		this.items = items;
	}
	public String getTitulo() {
		return titulo;
	}
	public Boolean getIsNotEmpty() {
		return items!=null && !items.isEmpty();
	}
	public int getOrder() {
		return order;
	}
}