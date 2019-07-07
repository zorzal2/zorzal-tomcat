package com.fontar.proyecto.presupuesto.excel.parser.parsers.cf_general;

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
		ret.add(new Field(Labels.CFGeneral.itemBienes.DESCRIPCION, true));
		ret.add(new Field(Labels.CFGeneral.itemBienes.COSTO_TOTAL, true));
		return ret;
	}

	@Override
	protected PresupuestoItemBean crearItem(Map<Label, Cell> map) {
		PresupuestoItemBienBean item = new PresupuestoItemBienBean();
		//verifico que haya un item en esta fila
		Cell descripcion = map.get(Labels.CFGeneral.itemBienes.DESCRIPCION);
		item.setNombre(descripcion.getContents());
		
		Cell costoTotal = map.get(Labels.CFGeneral.itemBienes.COSTO_TOTAL);
		item.setTotal(((NumberCell)costoTotal).getValue());

		item.setParte(0.0);
		item.setContraparte(item.getTotal());
		
		return item;
	}

	@Override
	protected PresupuestoItemBean checkItem(PresupuestoItemBean item) throws ValidationException {
		baseItemCheck(item);
		return item;
	}

}
