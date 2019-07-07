package com.fontar.web.action.presupuesto.anr;

import java.util.List;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDirectorExpertoDTO;
import com.fontar.web.action.presupuesto.Campo;
import com.fontar.web.action.presupuesto.RubroDetalleSettings;
import com.fontar.web.decorator.proyectos.presupuesto.PresupuestoItemWrapper;
import com.pragma.util.StringUtil;

public class RubroDirectorExpertoSettings extends RubroDetalleSettings {

	@Override
	protected void populateCampos(List<Campo> campos) {
		campos.add(new Campo("app.header.costoMensual", "costoMensual", "text-align: right"));
		campos.add(new Campo("app.header.dedicacion", "dedicacion", "text-align: right"));
		campos.add(new Campo("app.header.costoTotal", "montoTotal", "text-align: right"));
		campos.add(new Campo("app.header.costoParte", "montoParte", "text-align: right"));
		campos.add(new Campo("app.header.costoContraparte", "montoContraparte", "text-align: right"));
	}

	@Override
	protected PresupuestoItemWrapper transform(PresupuestoItemDTO item) {
		PresupuestoItemDirectorExpertoDTO dto = (PresupuestoItemDirectorExpertoDTO) item;
		PresupuestoItemWrapper wrapper = new PresupuestoItemWrapper();
		wrapper.setMontoTotal(StringUtil.formatMoneyForPresentation(dto.getTotal()));
		wrapper.setMontoParte(StringUtil.formatMoneyForPresentation(dto.getParte()));
		wrapper.setMontoContraparte(StringUtil.formatMoneyForPresentation(dto.getContraparte()));
		wrapper.setCostoMensual(StringUtil.formatMoneyForPresentation(dto.getCostoMensual()));
		double dedicacion = dto.getDedicacion().doubleValue();
		dedicacion *= 100.0;
		wrapper.setDedicacion(String.valueOf(dedicacion)+"%");
		return wrapper;
	}

}