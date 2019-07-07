/**
 * 
 */
package com.fontar.data.impl.domain.dto.proyecto.presupuesto.detalle;

import java.util.ArrayList;
import java.util.List;
/**
 * Representa una fila de la tabla de flujos. Tiene un titulo que puede ser un
 * nombre de rubro o la palabra "Total" y la lista de valores correspondientes 
 * a cada periodo (usualmente 12):
 * { "Insumos", 0.00, 1150.00, 11200.00, ..., 0.00, 1150.00, 11200.00, 5000.00 }
 * @author llobeto
 *
 */
public class BloqueFlujoDetalleDTO {
	int order;
	String titulo;
	List<String> valores = new ArrayList<String>();
	public BloqueFlujoDetalleDTO(String titulo, int order) {
		this.titulo = titulo;
		this.order = order;
	}
	public String getTitulo() {
		return titulo;
	}
	public List<String> getValores() {
		return valores;
	}
	public void addValor(String valor) {
		this.valores.add(valor);
	}
	public int getOrder() {
		return order;
	}
}