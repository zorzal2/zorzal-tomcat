package com.fontar.proyecto.presupuesto.excel.parser.parsers.consejerias;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import jxl.Cell;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemCanonInstitucionalBean;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.BaseRubroParser;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.Field;
import com.pragma.excel.parser.util.Label;

public class RubroCanonInstitucionalParser extends BaseRubroParser {

	public RubroCanonInstitucionalParser(RubroBean rubro) {
		super(rubro);
	}

	@Override
	protected Collection<Field> getCampos() {
		Collection<Field> ret = new ArrayList<Field>();
		ret.add(new Field(Labels.Consejerias.itemCanonInstitucional.EMPRESA, true));
		ret.add(new Field(Labels.Consejerias.itemCanonInstitucional.PARTE, false));
		ret.add(new Field(Labels.Consejerias.itemCanonInstitucional.CONTRAPARTE, false));
		ret.add(new Field(Labels.Consejerias.itemCanonInstitucional.TOTAL, false));
		return ret;
	}

	@Override
	protected PresupuestoItemBean crearItem(Map<Label, Cell> map) {
		PresupuestoItemCanonInstitucionalBean item = new PresupuestoItemCanonInstitucionalBean();
		//verifico que haya un item en esta fila
		Cell empresa = map.get(Labels.Consejerias.itemCanonInstitucional.EMPRESA);
		item.setNombre(empresa.getContents());

		Cell parte = map.get(Labels.Consejerias.itemCanonInstitucional.PARTE);
		item.setParte(numberOrNull(parte));

		Cell contraparte = map.get(Labels.Consejerias.itemCanonInstitucional.CONTRAPARTE);
		item.setContraparte(numberOrNull(contraparte));
		
		Cell total = map.get(Labels.Consejerias.itemCanonInstitucional.TOTAL);
		item.setTotal(numberOrNull(total));
		return item;
	}

	@Override
	protected PresupuestoItemBean checkItem(PresupuestoItemBean item) throws ValidationException {
		baseItemCheck(item);
		return item;
	}

}
