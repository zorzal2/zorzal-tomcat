package com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item;

import com.pragma.util.interfaces.Bean;

public class PresupuestoItemBean implements Bean {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombre;
	private Double parte;
	private Double contraparte;
	private Double total;
	
	public Double getContraparte() {
		return contraparte;
	}
	public void setContraparte(Double contraparte) {
		this.contraparte = contraparte;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getParte() {
		return parte;
	}
	public void setParte(Double parte) {
		this.parte = parte;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<");
		buffer.append(getNombre());
		buffer.append(": Parte=");
		buffer.append(getParte());
		buffer.append(", Contraparte=");
		buffer.append(getContraparte());
		buffer.append(">");
		return buffer.toString();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
