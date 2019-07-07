package com.fontar.proyecto.presupuesto.excel.parser.parsers.cf_general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import jxl.Cell;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoPropioBean;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.BaseRubroParser;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.Field;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;

public class RubroRecursoHumanoPropioParser extends BaseRubroParser {

	public RubroRecursoHumanoPropioParser(RubroBean rubro) {
		super(rubro);
	}

	@Override
	protected Collection<Field> getCampos() {
		Collection<Field> ret = new ArrayList<Field>();
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosPropios.NOMBRE, true));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosPropios.PROFESION, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosPropios.ID_TRIBUTARIA, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosPropios.FUNCION, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosPropios.COSTO_MENSUAL, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosPropios.PARTICIPACION, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosPropios.DEDICACION, false));
		ret.add(new Field(Labels.CFGeneral.itemRecursosHumanosPropios.COSTO_TOTAL, true));
		return ret;
	}

	@Override
	protected PresupuestoItemBean crearItem(Map<Label, Cell> map) {
		PresupuestoItemRecursoHumanoBean item = new PresupuestoItemRecursoHumanoPropioBean();
		
		//verifico que haya un item en esta fila
		
		Cell nombre = map.get(Labels.CFGeneral.itemRecursosHumanosPropios.NOMBRE);
		item.setNombre(nombre.getContents());
		
		Cell profesion = map.get(Labels.CFGeneral.itemRecursosHumanosPropios.PROFESION);
		item.setProfesion(textOrNull(profesion));
		
		Cell idTributaria = map.get(Labels.CFGeneral.itemRecursosHumanosPropios.ID_TRIBUTARIA);
		item.setIdentificacionTributaria(textOrNull(idTributaria));

		Cell funcion = map.get(Labels.CFGeneral.itemRecursosHumanosPropios.FUNCION);
		item.setFuncion(textOrNull(funcion));
		
		Cell costoMensual = map.get(Labels.CFGeneral.itemRecursosHumanosPropios.COSTO_MENSUAL);
		item.setCostoMensual(numberOrNull(costoMensual));

		Cell dedicacion = map.get(Labels.CFGeneral.itemRecursosHumanosPropios.DEDICACION);
		item.setDedicacion(numberOrNull(dedicacion));
		
		Cell participacion = map.get(Labels.CFGeneral.itemRecursosHumanosPropios.PARTICIPACION);
		item.setParticipacion(numberOrNull(participacion));
		
		Cell costoTotal = map.get(Labels.CFGeneral.itemRecursosHumanosPropios.COSTO_TOTAL);
		item.setTotal(numberOrNull(costoTotal));
		//Asumo que lo paga integramente la contraparte.
		item.setParte(0.0D);
		item.setContraparte(item.getTotal());
		return item;
	}

	@Override
	protected PresupuestoItemBean checkItem(PresupuestoItemBean item) throws ValidationException {
		baseItemCheck(item);
		PresupuestoItemRecursoHumanoBean pirhItem= (PresupuestoItemRecursoHumanoBean) item;
		obligatorios(
				pirhItem.getCostoMensual(),
				pirhItem.getParticipacion(),
				pirhItem.getDedicacion()		
		);
		if(!ParseUtils.similar(pirhItem.getTotal(), pirhItem.getCostoMensual() * pirhItem.getParticipacion() * (pirhItem.getDedicacion()/100.0)))
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.rh.totalIncoherente(pirhItem, getRubro()));
		return item;
	}
}