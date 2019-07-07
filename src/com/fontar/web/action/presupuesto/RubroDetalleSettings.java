package com.fontar.web.action.presupuesto;

import java.util.ArrayList;
import java.util.List;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.fontar.web.decorator.proyectos.presupuesto.PresupuestoItemWrapper;
import com.pragma.util.Transformations.Transformation;

public abstract class RubroDetalleSettings {
	/* 
	 * Clase auxiliar privada
	 * Transformacion a partir de un metodo de la clase RubroDetalleSettings  
	 */
	private static class ItemTransformation implements Transformation<PresupuestoItemDTO, PresupuestoItemWrapper> {
		private RubroDetalleSettings settings;
		public ItemTransformation(RubroDetalleSettings settings) {
			this.settings = settings;
		}
		public PresupuestoItemWrapper applyTo(PresupuestoItemDTO item) {
			return settings.transform(item);
		}
	}

	private List<Campo> campos;
	private Transformation<PresupuestoItemDTO, PresupuestoItemWrapper> transform ;
	
	public RubroDetalleSettings() {
		campos = new ArrayList<Campo>();
		populateCampos(campos);
		transform = new ItemTransformation(this);
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}

	public Transformation<PresupuestoItemDTO, PresupuestoItemWrapper> getTransformation() {
		return transform;
	}

	protected abstract void populateCampos(List<Campo> campos);
	protected abstract PresupuestoItemWrapper transform(PresupuestoItemDTO item);
}
