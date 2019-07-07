package com.fontar.proyecto.presupuesto.excel.parser.parsers.arai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import jxl.Cell;
import jxl.NumberCell;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemInsumoBean;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.BaseRubroParser;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.Field;
import com.pragma.excel.parser.util.Label;

public class RubroInsumoParser extends BaseRubroParser {

	public RubroInsumoParser(RubroBean rubro) {
		super(rubro);
	}

	@Override
	protected Collection<Field> getCampos() {
		Collection<Field> ret = new ArrayList<Field>();
		ret.add(new Field(Labels.ARAI.itemInsumos.DETALLE, true));
		ret.add(new Field(Labels.ARAI.itemInsumos.UNIDAD_DE_MEDIDA, false));
		ret.add(new Field(Labels.ARAI.itemInsumos.CANTIDAD, false));
		ret.add(new Field(Labels.ARAI.itemInsumos.COSTO_UNITARIO, false));
		ret.add(new Field(Labels.ARAI.itemInsumos.COSTO_TOTAL, true));
		ret.add(new Field(Labels.ARAI.itemInsumos.PARTE, false));
		ret.add(new Field(Labels.ARAI.itemInsumos.CONTRAPARTE, false));
		return ret;
	}

	@Override
	protected PresupuestoItemBean crearItem(Map<Label, Cell> map) {
		PresupuestoItemInsumoBean item = new PresupuestoItemInsumoBean();
		
		//verifico que haya un item en esta fila
		Cell detalle = map.get(Labels.ARAI.itemInsumos.DETALLE);
		item.setNombre(detalle.getContents());
		
		Cell unidadDeMedida = map.get(Labels.ARAI.itemInsumos.UNIDAD_DE_MEDIDA);
		item.setUnidadMedida(textOrNull(unidadDeMedida));

		Cell cantidad = map.get(Labels.ARAI.itemInsumos.CANTIDAD);
		item.setCantidad(numberOrNull(cantidad));
		
		Cell costoUnitario = map.get(Labels.ARAI.itemInsumos.COSTO_UNITARIO);
		item.setCostoUnitario(numberOrNull(costoUnitario));

		Cell costoTotal = map.get(Labels.ARAI.itemInsumos.COSTO_TOTAL);
		item.setTotal(((NumberCell)costoTotal).getValue());
		
		Cell parte = map.get(Labels.ARAI.itemInsumos.PARTE);
		item.setParte(numberOrNull(parte));

		Cell contraparte = map.get(Labels.ARAI.itemInsumos.CONTRAPARTE);
		item.setContraparte(numberOrNull(contraparte));

		return item;
	}

	@Override
	protected PresupuestoItemBean checkItem(PresupuestoItemBean item) throws ValidationException {
		baseItemCheck(item);
		PresupuestoItemInsumoBean piiItem = (PresupuestoItemInsumoBean) item;
		obligatorios(piiItem.getCantidad(), piiItem.getCostoUnitario());
		if(piiItem.getTotal()!=piiItem.getCantidad()*piiItem.getCostoUnitario())
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.insumo.totalIncoherente(piiItem, getRubro()));
		return piiItem;
	}

}
