package com.fontar.proyecto.presupuesto.excel.parser.parsers.anr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import jxl.Cell;
import jxl.NumberCell;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBienBean;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.BaseRubroParser;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.Field;
import com.pragma.excel.parser.util.Label;

public class RubroBienParser extends BaseRubroParser {

	public RubroBienParser(RubroBean rubro) {
		super(rubro);
	}

	@Override
	protected Collection<Field> getCampos() {
		Collection<Field> ret = new ArrayList<Field>();
		ret.add(new Field(Labels.ANR.itemBienes.DESCRIPCION, true));
		ret.add(new Field(Labels.ANR.itemBienes.COSTO_TOTAL, true));
		ret.add(new Field(Labels.ANR.itemBienes.PARTE, false));
		ret.add(new Field(Labels.ANR.itemBienes.CONTRAPARTE, false));
		return ret;
	}

	@Override
	protected PresupuestoItemBean crearItem(Map<Label, Cell> map) {
		PresupuestoItemBienBean item = new PresupuestoItemBienBean();
		//verifico que haya un item en esta fila
		Cell descripcion = map.get(Labels.ANR.itemBienes.DESCRIPCION);
		item.setNombre(descripcion.getContents());
		
		Cell costoTotal = map.get(Labels.ANR.itemBienes.COSTO_TOTAL);
		item.setTotal(((NumberCell)costoTotal).getValue());

		Cell parte = map.get(Labels.ANR.itemBienes.PARTE);
		item.setParte(numberOrNull(parte));

		Cell contraparte = map.get(Labels.ANR.itemBienes.CONTRAPARTE);
		item.setContraparte(numberOrNull(contraparte));
		
		return item;
	}

	@Override
	protected PresupuestoItemBean checkItem(PresupuestoItemBean item) throws ValidationException {
		baseItemCheck(item);
		return item;
	}

}
