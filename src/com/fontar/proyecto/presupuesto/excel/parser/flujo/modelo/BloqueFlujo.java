package com.fontar.proyecto.presupuesto.excel.parser.flujo.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo.FlujoBean;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.ValidationException;

public class BloqueFlujo {
	private Map<RubroBean, ItemFlujo> items = new LinkedHashMap<RubroBean, ItemFlujo>();
	
	public Map<RubroBean, ItemFlujo> getItems() {
		return this.items;
	}
	public void addItem(ItemFlujo item) {
		this.items.put(item.getRubro(), item);
	}
	public void checkValid() throws ValidationException {
		ValidationException exception = new ValidationException();
		boolean dispararErrorDePeriodosDiferentes = false;
		//Los items son validos y tienen los mismos periodos
		Set<String> nombresDePeriodos = null;
		for(ItemFlujo item : items.values()) {
			try {
				item.checkValid();
			} catch(ValidationException e) {
				//Guardo errores y continuo
				exception.appendMessages(e.getMessages());
			}
			if(nombresDePeriodos==null) nombresDePeriodos = item.getValores().keySet();
			else if(!nombresDePeriodos.equals(item.getValores().keySet()))
				dispararErrorDePeriodosDiferentes = true;
		}
		if(dispararErrorDePeriodosDiferentes)
			exception.appendMessage(PresupuestoMessages.flujo.periodosDiferentes());
		if(exception.hasMessages()) throw exception;
	}
	public double valorDe(RubroBean rubro, String periodo) {
		return getItems().get(rubro).valorDePeriodo(periodo);
	}
	public Set<String> periodos() {
		Collection<ItemFlujo> _items = items.values();
		if(_items.isEmpty()) return new LinkedHashSet<String>();
		else {
			return _items.iterator().next().periodos();
		}
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(ItemFlujo item : items.values()) {
			buffer.append(item);
			buffer.append('\n');
		}
		return buffer.toString();
	}
	public List<FlujoBean> buildBean(PresupuestoRubroGeneralBean presupuestoRubro) {
		if(!items.containsKey(presupuestoRubro.getRubro())) {
			//No esta presente el flujo para este rubro
			return null;
		}
		
		List<FlujoBean> ret = new ArrayList<FlujoBean>();
		for(String nombreDePeriodo : periodos()) {
			FlujoBean bean = new FlujoBean();
			//bean.setPresupuestoRubro(presupuestoRubro);
			bean.setPeriodo(nombreDePeriodo);

			RubroBean rubroBean = presupuestoRubro.getRubro();
			
			double valorTotal = valorDe(rubroBean, nombreDePeriodo);
			bean.setParte(0.0);
			bean.setContraparte(valorTotal);
			bean.setTotal(valorTotal);
			
			ret.add(bean);
		}
		return ret;
	}
}
