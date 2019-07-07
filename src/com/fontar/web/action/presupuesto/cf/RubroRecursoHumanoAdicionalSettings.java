/**
 * 
 */
package com.fontar.web.action.presupuesto.cf;

import java.util.List;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoAdicionalDTO;
import com.fontar.web.action.presupuesto.Campo;
import com.fontar.web.action.presupuesto.RubroDetalleSettings;
import com.fontar.web.decorator.proyectos.presupuesto.PresupuestoItemWrapper;
import com.pragma.util.StringUtil;

public class RubroRecursoHumanoAdicionalSettings extends RubroDetalleSettings {
	
	protected PresupuestoItemWrapper transform(PresupuestoItemDTO item) {
		
		PresupuestoItemRecursoHumanoAdicionalDTO dto = (PresupuestoItemRecursoHumanoAdicionalDTO) item;
		PresupuestoItemWrapper wrapper = new PresupuestoItemWrapper();
		wrapper.setMontoTotal(StringUtil.formatMoneyForPresentation(dto.getTotal()));
		wrapper.setIdentificacionTributaria(dto.getIdentificacionTributaria()==null ? "" : dto.getIdentificacionTributaria());
		wrapper.setFuncion(dto.getFuncion());
		wrapper.setCostoMensual(StringUtil.formatMoneyForPresentation(dto.getCostoMensual()));
		wrapper.setDedicacion(dto.getDedicacion().toString()+"%");
		wrapper.setParticipacion(dto.getParticipacion().toString());
		wrapper.setProfesion(dto.getProfesion());
		return wrapper;
	}
	@Override
	protected void populateCampos(List<Campo> campos) {
		campos.add(new Campo("app.header.profesion", "profesion"));
		campos.add(new Campo("app.header.identificacionTributaria", "identificacionTributaria"));
		campos.add(new Campo("app.header.funcion", "funcion"));
		campos.add(new Campo("app.header.costoMensual", "costoMensual", "text-align: right"));
		campos.add(new Campo("app.header.dedicacion", "dedicacion", "text-align: right"));
		campos.add(new Campo("app.header.participacion", "participacion", "text-align: right"));
		
		campos.add(new Campo("app.header.costoTotal", "montoTotal", "text-align: right"));
	}			
}