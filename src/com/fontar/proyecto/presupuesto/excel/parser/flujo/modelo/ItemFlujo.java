package com.fontar.proyecto.presupuesto.excel.parser.flujo.modelo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.ValidationException;

public class ItemFlujo {
	private RubroBean rubro;
	private Map<String, Double> valores;

	public RubroBean getRubro() {
		return rubro;
	}
	public void setRubro(RubroBean rubro) {
		this.rubro = rubro;
	}
	public Map<String, Double> getValores() {
		return valores;
	}
	public void checkValid() throws ValidationException {
		//Por ahora solo chequeo que los valores sean >=0
		for(Double valor : valores.values())
			if(valor<0) throw new ValidationException(PresupuestoMessages.flujo.valoresNegativos(rubro));
	}
	public void add(String periodo, Double valor) {
		if(this.valores==null) this.valores = new LinkedHashMap<String, Double>();
		this.valores.put(periodo, valor);
	}
	public Double valorDePeriodo(String periodo) {
		return getValores().get(periodo);
	}
	public Set<String> periodos() {
		return valores.keySet();
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(rubro.getNombre());
		for(Entry<String, Double> entry : valores.entrySet()) {
			buffer.append(",\t");
			buffer.append("[");
			buffer.append(entry.getKey());
			buffer.append("]:");
			buffer.append(entry.getValue());
		}
		return buffer.toString();
	}
}
