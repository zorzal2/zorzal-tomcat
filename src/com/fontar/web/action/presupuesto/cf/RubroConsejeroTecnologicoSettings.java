package com.fontar.web.action.presupuesto.cf;

import java.util.List;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemConsejeroTecnologicoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.fontar.web.action.presupuesto.Campo;
import com.fontar.web.action.presupuesto.RubroDetalleSettings;
import com.fontar.web.decorator.proyectos.presupuesto.PresupuestoItemWrapper;
import com.pragma.util.StringUtil;

public class RubroConsejeroTecnologicoSettings extends RubroDetalleSettings {

	@Override
	protected void populateCampos(List<Campo> campos) {
		campos.add(new Campo("app.header.nombre", "nombre"));
		campos.add(new Campo("app.header.profesion", "profesion"));
		campos.add(new Campo("app.header.identificacionTributaria", "identificacionTributaria"));
		campos.add(new Campo("app.header.funcion", "funcion"));
		campos.add(new Campo("app.header.sueldoMensual", "costoMensual", "text-align: right"));
		campos.add(new Campo("app.header.dedicacion", "dedicacion", "text-align: right"));
		campos.add(new Campo("app.header.participacion", "participacion", "text-align: right"));
		campos.add(new Campo("app.header.costoTotal", "montoTotal", "text-align: right"));
	}

	@Override
	protected PresupuestoItemWrapper transform(PresupuestoItemDTO item) {
		PresupuestoItemConsejeroTecnologicoDTO dto = (PresupuestoItemConsejeroTecnologicoDTO) item;
		PresupuestoItemWrapper wrapper = new PresupuestoItemWrapper();
		wrapper.setNombre(dto.getNombre());
		wrapper.setProfesion(dto.getProfesion());
		wrapper.setMontoTotal(StringUtil.formatMoneyForPresentation(dto.getTotal()));
		wrapper.setMontoParte(StringUtil.formatMoneyForPresentation(dto.getParte()));
		wrapper.setMontoContraparte(StringUtil.formatMoneyForPresentation(dto.getContraparte()));
		wrapper.setIdentificacionTributaria(dto.getIdentificacionTributaria());
		wrapper.setCostoMensual(StringUtil.formatMoneyForPresentation(dto.getCostoMensual()));
		wrapper.setDedicacion(dto.getDedicacion().toString()+"%");
		wrapper.setParticipacion(dto.getParticipacion().toString());
		wrapper.setFuncion(dto.getFuncion());
		return wrapper;
	}

}