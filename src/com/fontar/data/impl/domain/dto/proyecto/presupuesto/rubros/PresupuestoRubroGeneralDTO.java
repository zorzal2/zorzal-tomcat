package com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros;

import java.util.List;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.flujo.FlujoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;

public class PresupuestoRubroGeneralDTO extends PresupuestoRubroDTO {
	private List<PresupuestoItemDTO> items;
	private List<FlujoDTO> flujoDeFondos;
	public List<PresupuestoItemDTO> getItems() {
		return items;
	}
	public void setItems(List<PresupuestoItemDTO> items) {
		this.items = items;
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getRubro().getNombre());
		buffer.append(": {");
		if(getItems()!=null) {
			for (PresupuestoItemDTO item : getItems()) {
				buffer.append(item);
				buffer.append("\n");
			}
		}
		buffer.append("}");
		return buffer.toString();
	}
	public List<FlujoDTO> getFlujoDeFondos() {
		return flujoDeFondos;
	}
	public void setFlujoDeFondos(List<FlujoDTO> flujoDeFondos) {
		this.flujoDeFondos = flujoDeFondos;
	}
}
