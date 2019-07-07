package com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros;

import java.util.List;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo.FlujoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;

public class PresupuestoRubroGeneralBean extends PresupuestoRubroBean {

	private static final long serialVersionUID = 1L;
	private List<PresupuestoItemBean> items;
	private List<FlujoBean> flujoDeFondos;
	public List<PresupuestoItemBean> getItems() {
		return items;
	}
	public void setItems(List<PresupuestoItemBean> items) {
		this.items = items;
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getRubro().getNombre());
		buffer.append(": {");
		if(getItems()!=null) {
			for (PresupuestoItemBean item : getItems()) {
				buffer.append(item);
				buffer.append("\n");
			}
		}
		buffer.append("}");
		return buffer.toString();
	}
	public List<FlujoBean> getFlujoDeFondos() {
		return flujoDeFondos;
	}
	public void setFlujoDeFondos(List<FlujoBean> flujoDeFondos) {
		this.flujoDeFondos = flujoDeFondos;
	}
}
