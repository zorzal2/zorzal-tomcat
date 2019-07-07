package com.fontar.proyecto.presupuesto.excel.parser.parsers.cf_general;

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
		ret.add(new Field(Labels.CFConsejerias.itemConsejeroTecnologico.NOMBRE, true));
		ret.add(new Field(Labels.CFConsejerias.itemConsejeroTecnologico.PROFESION, false));
		ret.add(new Field(Labels.CFConsejerias.itemConsejeroTecnologico.ID_TRIBUTARIA, false));
		ret.add(new Field(Labels.CFConsejerias.itemConsejeroTecnologico.FUNCION, false));
		ret.add(new Field(Labels.CFConsejerias.itemConsejeroTecnologico.COSTO_MENSUAL, false));
		ret.add(new Field(Labels.CFConsejerias.itemConsejeroTecnologico.DEDICACION, false));
		ret.add(new Field(Labels.CFConsejerias.itemConsejeroTecnologico.PARTICIPACION, false));
		ret.add(new Field(Labels.CFConsejerias.itemConsejeroTecnologico.COSTO_TOTAL, true));
		return ret;
	}

	@Override
	protected PresupuestoItemBean crearItem(Map<Label, Cell> map) {
		PresupuestoItemConsejeroTecnologicoBean item = new PresupuestoItemConsejeroTecnologicoBean();
		//verifico que haya un item en esta fila
		Cell nombre = map.get(Labels.CFConsejerias.itemConsejeroTecnologico.NOMBRE);
		item.setNombre(nombre.getContents());
		
		Cell profesion = map.get(Labels.CFConsejerias.itemConsejeroTecnologico.PROFESION);
		item.setProfesion(textOrNull(profesion));
		
		Cell funcion = map.get(Labels.CFConsejerias.itemConsejeroTecnologico.FUNCION);
		item.setFuncion(textOrNull(funcion));
		
		Cell idTributaria = map.get(Labels.CFConsejerias.itemConsejeroTecnologico.ID_TRIBUTARIA);
		item.setIdentificacionTributaria(textOrNull(idTributaria));
		
		Cell costoMensual = map.get(Labels.CFConsejerias.itemConsejeroTecnologico.COSTO_MENSUAL);
		item.setCostoMensual(numberOrNull(costoMensual));
		
		Cell participacion = map.get(Labels.CFConsejerias.itemConsejeroTecnologico.PARTICIPACION);
		item.setParticipacion(numberOrNull(participacion));

		Cell dedicacion = map.get(Labels.CFConsejerias.itemConsejeroTecnologico.DEDICACION);
		item.setDedicacion(numberOrNull(dedicacion));
		
		Cell costoTotal = map.get(Labels.CFConsejerias.itemConsejeroTecnologico.COSTO_TOTAL);
		item.setTotal(numberOrNull(costoTotal));
		
		item.setTitulo(null);
		item.setCategoria(null);
		item.setParte(0.0);
		item.setContraparte(item.getTotal());
		
		return item;
	}

	@Override
	protected PresupuestoItemBean checkItem(PresupuestoItemBean item) throws ValidationException {
		baseItemCheck(item);
		PresupuestoItemConsejeroTecnologicoBean consejero = (PresupuestoItemConsejeroTecnologicoBean) item;
		obligatorios(
			consejero.getCostoMensual(),
			consejero.getDedicacion(),
			consejero.getParticipacion()	
		);
		if(!ParseUtils.similar(consejero.getTotal(), consejero.getCostoMensual() * consejero.getParticipacion() * (consejero.getDedicacion()/100.0)))
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.rh.totalIncoherente(consejero, getRubro()));
		return item;
	}

}
