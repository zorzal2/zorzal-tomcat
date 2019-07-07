package com.fontar.proyecto.presupuesto.excel.parser.flujo.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo.FlujoBean;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.ParseUtils;

public class Flujo {
	private BloqueFlujo parte = new BloqueFlujo();
	private BloqueFlujo contraparte = new BloqueFlujo();
	private BloqueFlujo total = new BloqueFlujo();
	
	public void addItemParte(ItemFlujo itemFlujo) {
		parte.addItem(itemFlujo);
	}
	public void addItemContraparte(ItemFlujo itemFlujo) {
		contraparte.addItem(itemFlujo);
	}
	public void addItemTotal(ItemFlujo itemFlujo) {
		total.addItem(itemFlujo);
	}
	public void checkValid() throws ValidationException {
		ValidationException exception = new ValidationException();
		
		//Los bloques son validos?
		try {
			parte.checkValid();
		} catch(ValidationException e) {
			exception.appendMessages(e.getMessages());
		}
		try {
			contraparte.checkValid(); 
		} catch(ValidationException e) {
			exception.appendMessages(e.getMessages());
		}
		try {
			total.checkValid();
		} catch(ValidationException e) {
			exception.appendMessages(e.getMessages());
		}
		if(exception.hasMessages()) throw exception;
		
		//los mismos rubros estan presentes en todos los flujos
		boolean todosLosRubrosPresentes =
			parte.getItems().keySet().equals(contraparte.getItems().keySet()) &&
			total.getItems().keySet().equals(contraparte.getItems().keySet());
		if(!todosLosRubrosPresentes) 
			exception.appendMessage(PresupuestoMessages.flujo.faltanFlujos());
		//los mismos periodos estan presentes en todos los flujos
		boolean todosLosPeriodosPresentes =
			parte.periodos().equals(contraparte.periodos()) &&
			total.periodos().equals(contraparte.periodos());
		if(!todosLosPeriodosPresentes)
			exception.appendMessage(PresupuestoMessages.flujo.periodosDiferentes());
		
		if(exception.hasMessages()) throw exception;
		
		//el total es igual a la suma de las partes
		for(RubroBean rubro : total.getItems().keySet()) {
			for(String periodo : total.periodos()) {
				if(!ParseUtils.similar(
						total.valorDe(rubro, periodo),
						parte.valorDe(rubro, periodo) +
						contraparte.valorDe(rubro, periodo)
				)) exception.appendMessage(PresupuestoMessages.flujo.totalDifiereDePartes(periodo));
			}
		}
		if(exception.hasMessages()) throw exception;
	}
	public List<FlujoBean> buildBean(PresupuestoRubroGeneralBean presupuestoRubro) {
		List<FlujoBean> ret = new ArrayList<FlujoBean>();
		if(!this.getRubros().contains(presupuestoRubro.getRubro())) return null;
		for(String nombreDePeriodo : getTotal().periodos()) {
			FlujoBean bean = new FlujoBean();
			//bean.setPresupuestoRubro(presupuestoRubro);
			bean.setPeriodo(nombreDePeriodo);

			RubroBean rubroBean = presupuestoRubro.getRubro();
			
			bean.setParte(parte.valorDe(rubroBean, nombreDePeriodo));
			bean.setContraparte(contraparte.valorDe(rubroBean, nombreDePeriodo));
			bean.setTotal(total.valorDe(rubroBean, nombreDePeriodo));
			
			ret.add(bean);
		}
		return ret;
	}
	public BloqueFlujo getContraparte() {
		return contraparte;
	}
	public void setContraparte(BloqueFlujo contraparte) {
		this.contraparte = contraparte;
	}
	public BloqueFlujo getParte() {
		return parte;
	}
	public void setParte(BloqueFlujo parte) {
		this.parte = parte;
	}
	public BloqueFlujo getTotal() {
		return total;
	}
	public void setTotal(BloqueFlujo total) {
		this.total = total;
	}
	/*
	 * Rubros para los cuales hay definido un flujo.
	 */
	public Collection<RubroBean> getRubros() {
		return total.getItems().keySet();
	}
	public String toString() {
		StringBuffer retBuffer = new StringBuffer();
		retBuffer.append("Parte\n-----\n");
		retBuffer.append(parte);
		retBuffer.append("Contraparte\n-----------\n");
		retBuffer.append(contraparte);
		retBuffer.append("Total\n-----\n");
		retBuffer.append(total);
		return retBuffer.toString();
	}
}
