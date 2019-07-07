package com.fontar.web.action.presupuesto;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemBienDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemConsultorDTO;
import com.fontar.web.action.presupuesto.patente.RubroBienSettings;
import com.fontar.web.action.presupuesto.patente.RubroConsultorSettings;


 

/**
 * Action de la tarea de ingreso de presupuesto a un proyecto
 * @author llobeto
 */
public class PresupuestoDetalleRubrosPatenteAction extends PresupuestoDetalleRubrosAction {

	@Override
	protected void fillSettings() {
		addSetting(PresupuestoItemBienDTO.class, new RubroBienSettings());
		addSetting(PresupuestoItemConsultorDTO.class, new RubroConsultorSettings());
	}

}
