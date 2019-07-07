package com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo;


public class FlujoBean {
	private Long id;
	private Double parte;
	private Double contraparte;
	private Double total;
	private String periodo;
	public Double getContraparte() {
		return contraparte;
	}
	public void setContraparte(Double contraparte) {
		this.contraparte = contraparte;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getParte() {
		return parte;
	}
	public void setParte(Double parte) {
		this.parte = parte;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
}
