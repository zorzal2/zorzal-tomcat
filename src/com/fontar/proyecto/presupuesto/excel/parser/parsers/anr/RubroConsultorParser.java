package com.fontar.proyecto.presupuesto.excel.parser.parsers.anr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import jxl.Cell;
import jxl.NumberCell;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemConsultorBean;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.BaseRubroParser;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.Field;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;

public class RubroConsultorParser extends BaseRubroParser {

	public RubroConsultorParser(RubroBean rubro) {
		super(rubro);
	}

	@Override
	protected Collection<Field> getCampos() {
		Collection<Field> ret = new ArrayList<Field>();
		ret.add(new Field(Labels.ANR.itemConsultores.DESCRIPCION, true));
		ret.add(new Field(Labels.ANR.itemConsultores.COSTO_MENSUAL, false));
		ret.add(new Field(Labels.ANR.itemConsultores.PARTICIPACION, false));
		ret.add(new Field(Labels.ANR.itemConsultores.COSTO_TOTAL, true));
		ret.add(new Field(Labels.ANR.itemConsultores.PARTE, false));
		ret.add(new Field(Labels.ANR.itemConsultores.CONTRAPARTE, false));
		return ret;
	}

	@Override
	protected PresupuestoItemBean crearItem(Map<Label, Cell> map) {
		PresupuestoItemConsultorBean item = new PresupuestoItemConsultorBean();

		Cell descripcion = map.get(Labels.ANR.itemConsultores.DESCRIPCION);
		item.setNombre(descripcion.getContents());

		Cell costoMensual = map.get(Labels.ANR.itemConsultores.COSTO_MENSUAL);
		item.setCostoMensual(numberOrNull(costoMensual));

		Cell participacion = map.get(Labels.ANR.itemConsultores.PARTICIPACION);
		item.setParticipacion(numberOrNull(participacion));

		Cell costoTotal = map.get(Labels.ANR.itemConsultores.COSTO_TOTAL);
		item.setTotal(((NumberCell)costoTotal).getValue());

		Cell parte = map.get(Labels.ANR.itemConsultores.PARTE);
		item.setParte(numberOrNull(parte));
		
		Cell contraparte = map.get(Labels.ANR.itemConsultores.CONTRAPARTE);
		item.setContraparte(numberOrNull(contraparte));
		
		return item;
	}

	@Override
	protected PresupuestoItemBean checkItem(PresupuestoItemBean item) throws ValidationException {
		baseItemCheck(item);
		PresupuestoItemConsultorBean picItem= (PresupuestoItemConsultorBean) item;
		obligatorios(
			picItem.getCostoMensual(),
			picItem.getParticipacion()
		);
		if(!ParseUtils.similar(picItem.getCostoMensual()*picItem.getParticipacion(), item.getTotal()))
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.consultoria.totalIncoherente(picItem, getRubro()));
		return item;
	}
}
