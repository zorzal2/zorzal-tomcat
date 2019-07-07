package com.fontar.web.action.presupuesto.anr;

import java.util.List;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemInsumoDTO;
import com.fontar.web.action.presupuesto.Campo;
import com.fontar.web.action.presupuesto.RubroDetalleSettings;
import com.fontar.web.decorator.proyectos.presupuesto.PresupuestoItemWrapper;
import com.pragma.util.StringUtil;

public class RubroInsumoSettings extends RubroDetalleSettings {

	@Override
	protected void populateCampos(List<Campo> campos) {
		campos.add(new Campo("app.header.detalle", "nombre"));
		campos.add(new Campo("app.header.unidadDeMedida", "unidadMedida"));
		campos.add(new Campo("app.header.cantidad", "cantidad", "text-align: right"));
		campos.add(new Campo("app.header.costoUnitario", "costoUnitario", "text-align: right"));
	
		campos.add(new Campo("app.header.costoTotal", "montoTotal", "text-align: right"));
		campos.add(new Campo("app.header.costoParte", "montoParte", "text-align: right"));
		campos.add(new Campo("app.header.costoContraparte", "montoContraparte", "text-align: right"));
	}

	@Override
	protected PresupuestoItemWrapper transform(PresupuestoItemDTO item) {
		PresupuestoItemInsumoDTO dto = (PresupuestoItemInsumoDTO) item;
		PresupuestoItemWrapper wrapper = new PresupuestoItemWrapper();
		wrapper.setNombre(dto.getNombre());
		wrapper.setUnidadMedida(dto.getUnidadMedida());
		wrapper.setMontoTotal(StringUtil.formatMoneyForPresentation(dto.getTotal()));
		wrapper.setMontoParte(StringUtil.formatMoneyForPresentation(dto.getParte()));
		wrapper.setMontoContraparte(StringUtil.formatMoneyForPresentation(dto.getContraparte()));
		wrapper.setCantidad(String.valueOf(dto.getCantidad()));
		wrapper.setCostoUnitario(StringUtil.formatMoneyForPresentation(dto.getCostoUnitario()));
		return wrapper;
	}

}