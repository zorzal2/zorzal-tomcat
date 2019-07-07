package com.fontar.web.action.presupuesto;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemBienDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemConsejeroTecnologicoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemConsultorDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemInsumoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoAdicionalDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoPropioDTO;
import com.fontar.web.action.presupuesto.cf.RubroBienSettings;
import com.fontar.web.action.presupuesto.cf.RubroConsejeroTecnologicoSettings;
import com.fontar.web.action.presupuesto.cf.RubroConsultorSettings;
import com.fontar.web.action.presupuesto.cf.RubroInsumoSettings;
import com.fontar.web.action.presupuesto.cf.RubroRecursoHumanoAdicionalSettings;
import com.fontar.web.action.presupuesto.cf.RubroRecursoHumanoPropioSettings;




/**
 * Action de la tarea de ingreso de presupuesto a un proyecto
 * @author llobeto
 */
public class PresupuestoDetalleRubrosCFAction extends PresupuestoDetalleRubrosAction {

	@Override
	protected void fillSettings() {
		addSetting(PresupuestoItemBienDTO.class, new RubroBienSettings());
		addSetting(PresupuestoItemConsultorDTO.class, new RubroConsultorSettings());
		addSetting(PresupuestoItemInsumoDTO.class, new RubroInsumoSettings());
		addSetting(PresupuestoItemRecursoHumanoAdicionalDTO.class, new RubroRecursoHumanoAdicionalSettings());
		addSetting(PresupuestoItemRecursoHumanoPropioDTO.class, new RubroRecursoHumanoPropioSettings());
		addSetting(PresupuestoItemConsejeroTecnologicoDTO.class, new RubroConsejeroTecnologicoSettings());
	}
}	

