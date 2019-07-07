package com.fontar.proyecto.presupuesto.excel.parser.parsers.consejerias;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import jxl.Cell;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemConsejeroTecnologicoBean;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.BaseRubroParser;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.Field;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;

public class RubroConsejeroTecnologicoParser extends BaseRubroParser {

	public RubroConsejeroTecnologicoParser(RubroBean rubro) {
		super(rubro);
	}

	@Override
	protected Collection<Field> getCampos() {
		Collection<Field> ret = new ArrayList<Field>();
		ret.add(new Field(Labels.Consejerias.itemConsejeroTecnologico.TITULO, false));
		ret.add(new Field(Labels.Consejerias.itemConsejeroTecnologico.NOMBRE, false));
		ret.add(new Field(Labels.Consejerias.itemConsejeroTecnologico.ID_TRIBUTARIA, false));
		ret.add(new Field(Labels.Consejerias.itemConsejeroTecnologico.PARTICIPACION, false));
		ret.add(new Field(Labels.Consejerias.itemConsejeroTecnologico.COSTO_MENSUAL, false));
		ret.add(new Field(Labels.Consejerias.itemConsejeroTecnologico.DEDICACION, false));
		ret.add(new Field(Labels.Consejerias.itemConsejeroTecnologico.CATEGORIA, false));
		ret.add(new Field(Labels.Consejerias.itemConsejeroTecnologico.PARTE, false));
		ret.add(new Field(Labels.Consejerias.itemConsejeroTecnologico.CONTRAPARTE, false));
		ret.add(new Field(Labels.Consejerias.itemConsejeroTecnologico.COSTO_TOTAL, true));
		return ret;
	}

	@Override
	protected PresupuestoItemBean crearItem(Map<Label, Cell> map) {
		PresupuestoItemConsejeroTecnologicoBean item = new PresupuestoItemConsejeroTecnologicoBean();
		//verifico que haya un item en esta fila
		Cell titulo = map.get(Labels.Consejerias.itemConsejeroTecnologico.TITULO);
		item.setTitulo(textOrNull(titulo));

		Cell nombre = map.get(Labels.Consejerias.itemConsejeroTecnologico.NOMBRE);
		item.setNombre(textOrNull(nombre));
		
		Cell idTributaria = map.get(Labels.Consejerias.itemConsejeroTecnologico.ID_TRIBUTARIA);
		item.setIdentificacionTributaria(textOrNull(idTributaria));
		
		
		Cell participacion = map.get(Labels.Consejerias.itemConsejeroTecnologico.PARTICIPACION);
		item.setParticipacion(numberOrNull(participacion));
		
		Cell costoMensual = map.get(Labels.Consejerias.itemConsejeroTecnologico.COSTO_MENSUAL);
		item.setCostoMensual(numberOrNull(costoMensual));
		
		Cell dedicacion = map.get(Labels.Consejerias.itemConsejeroTecnologico.DEDICACION);
		item.setDedicacion(numberOrNull(dedicacion));

		Cell categoria = map.get(Labels.Consejerias.itemConsejeroTecnologico.CATEGORIA);
		item.setCategoria(textOrNull(categoria));

		if(
				item.getNombre()== null && 
				item.getIdentificacionTributaria()==null &&
				item.getTitulo()==null &&
				item.getParticipacion()==null &&
				item.getDedicacion()==null &&
				item.getCategoria()==null &&
				item.getCostoMensual() == null
		) return null;
		
		Cell parte = map.get(Labels.Consejerias.itemConsejeroTecnologico.PARTE);
		item.setParte(numberOrNull(parte));

		Cell contraparte = map.get(Labels.Consejerias.itemConsejeroTecnologico.CONTRAPARTE);
		item.setContraparte(numberOrNull(contraparte));
		
		Cell costoTotal = map.get(Labels.Consejerias.itemConsejeroTecnologico.COSTO_TOTAL);
		item.setTotal(numberOrNull(costoTotal));
		return item;
	}

	@Override
	protected PresupuestoItemBean checkItem(PresupuestoItemBean item) throws ValidationException {
		PresupuestoItemConsejeroTecnologicoBean consejero = (PresupuestoItemConsejeroTecnologicoBean) item;
		baseItemCheck(item);
		obligatorios(
			consejero.getCostoMensual(),
			consejero.getParticipacion(),
			consejero.getDedicacion()
		);
		if(!ParseUtils.similar(consejero.getTotal(), consejero.getCostoMensual() * consejero.getParticipacion() * (consejero.getDedicacion()/100.0)))
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.rh.totalIncoherente(consejero, getRubro()));
		return item;
	}

}
