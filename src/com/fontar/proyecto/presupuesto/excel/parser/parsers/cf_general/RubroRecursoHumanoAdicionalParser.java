package com.fontar.proyecto.presupuesto.excel.parser.parsers.cf_general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import jxl.Cell;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoAdicionalBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoBean;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.BaseRubroParser;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.Field;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;

public class RubroRecursoHumanoAdicionalParser extends BaseRubroParser {

	public RubroRecursoHumanoAdicionalParser(RubroBean rubro) {
		super(rubro);
	}

	@Override
	protected Collection<Field> getCampos() {
		Collection<Field> ret = new ArrayList<Field>();
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosAdicionales.PROFESION, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosAdicionales.FUNCION, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosAdicionales.ID_TRIBUTARIA, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosAdicionales.COSTO_MENSUAL, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosAdicionales.PARTICIPACION, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosAdicionales.DEDICACION, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosAdicionales.COSTO_TOTAL, false));
		return ret;
	}

	@Override
	protected PresupuestoItemBean crearItem(Map<Label, Cell> map) {
		PresupuestoItemRecursoHumanoBean item = new PresupuestoItemRecursoHumanoAdicionalBean();

		//verifico que haya un item en esta fila
		Cell profesion = map.get(Labels.CFGeneral.itemRecursosHumanosAdicionales.PROFESION);
		item.setProfesion(textOrNull(profesion));

		Cell funcion = map.get(Labels.CFGeneral.itemRecursosHumanosAdicionales.FUNCION);
		if(!ParseUtils.isString(funcion)) return null;
		item.setFuncion(funcion.getContents());
		
		Cell idTributaria = map.get(Labels.CFGeneral.itemRecursosHumanosAdicionales.ID_TRIBUTARIA);
		item.setIdentificacionTributaria(textOrNull(idTributaria));
		
		if(
				item.getFuncion()==null && 
				item.getProfesion()==null && 
				item.getIdentificacionTributaria()==null
			) 
			return null;
		
		Cell costoMensual = map.get(Labels.CFGeneral.itemRecursosHumanosAdicionales.COSTO_MENSUAL);
		item.setCostoMensual(numberOrNull(costoMensual));

		Cell participacion = map.get(Labels.CFGeneral.itemRecursosHumanosAdicionales.PARTICIPACION);
		item.setParticipacion(numberOrNull(participacion));

		Cell dedicacion = map.get(Labels.CFGeneral.itemRecursosHumanosAdicionales.DEDICACION);
		item.setDedicacion(numberOrNull(dedicacion));
		
		Cell costoTotal = map.get(Labels.CFGeneral.itemRecursosHumanosAdicionales.COSTO_TOTAL);
		item.setTotal(numberOrNull(costoTotal));
		
		item.setParte(0.0);
		item.setContraparte(numberOrNull(costoTotal));

		item.setNombre(item.getProfesion());
		return item;
	}

	@Override
	protected PresupuestoItemBean checkItem(PresupuestoItemBean item) throws ValidationException {
		baseItemCheck(item);
		PresupuestoItemRecursoHumanoBean pirhItem= (PresupuestoItemRecursoHumanoBean) item;
		obligatorios(pirhItem.getCostoMensual(), pirhItem.getParticipacion(), pirhItem.getDedicacion());
		if(!ParseUtils.similar(pirhItem.getTotal(), pirhItem.getCostoMensual() * pirhItem.getParticipacion() * (pirhItem.getDedicacion()/100.0)))
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.rh.totalIncoherente(pirhItem, getRubro()));
		return item;
	}
}
