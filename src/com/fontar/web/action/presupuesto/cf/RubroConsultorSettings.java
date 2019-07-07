/**
 * 
 */
package com.fontar.web.action.presupuesto.cf;

import java.util.List;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemConsultorDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.fontar.web.action.presupuesto.Campo;
import com.fontar.web.action.presupuesto.RubroDetalleSettings;
import com.fontar.web.decorator.proyectos.presupuesto.PresupuestoItemWrapper;
import com.pragma.util.StringUtil;

public class RubroConsultorSettings extends RubroDetalleSettings {
	
	protected PresupuestoItemWrapper transform(PresupuestoItemDTO item) {
		
		PresupuestoItemConsultorDTO dto = (PresupuestoItemConsultorDTO)item;
		PresupuestoItemWrapper wrapper = new PresupuestoItemWrapper();
		wrapper.setMontoTotal(StringUtil.formatMoneyForPresentation(dto.getTotal()));
		wrapper.setNombre(dto.getNombre());
		wrapper.setCostoMensual(StringUtil.formatMoneyForPresentation(dto.getCostoMensual()));
		wrapper.setParticipacion(dto.getParticipacion().toString());
		return wrapper;
	}

	@Override
	protected void populateCampos(List<Campo> campos) {
		campos.add(new Campo("app.header.descripcion", "nombre"));
		campos.add(new Campo("app.header.costoMensual", "costoMensual", "text-align: right"));
		campos.add(new Campo("app.header.participacion", "participacion", "text-align: right"));
		
		campos.add(new Campo("app.header.costoTotal", "montoTotal", "text-align: right"));
	}			
}