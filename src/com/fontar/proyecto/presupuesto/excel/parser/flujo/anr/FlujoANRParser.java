package com.fontar.proyecto.presupuesto.excel.parser.flujo.anr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.NumberCell;

import com.fontar.data.api.dao.proyecto.RubroDAO;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.proyecto.presupuesto.excel.parser.flujo.modelo.BloqueFlujo;
import com.fontar.proyecto.presupuesto.excel.parser.flujo.modelo.Flujo;
import com.fontar.proyecto.presupuesto.excel.parser.flujo.modelo.ItemFlujo;
import com.fontar.proyecto.presupuesto.excel.parser.labels.DefaultLabels;
import com.pragma.excel.exception.ParsingException;
import com.pragma.excel.parser.util.FieldMatching;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;
import com.pragma.excel.parser.util.Parser;
import com.pragma.excel.parser.util.cusor.WorkbookCursor;
import com.pragma.util.ContextUtil;

public class FlujoANRParser implements Parser<Flujo> {
	
	private Collection<RubroBean> rubros = new ArrayList<RubroBean>();
	private ItemFlujoConfigANR config = new ItemFlujoConfigANR();
	private DefaultLabels labels;

	public FlujoANRParser(DefaultLabels labels) {
		//obtengo los nombres de los rubros que no tienen padre
		RubroDAO dao = (RubroDAO)ContextUtil.getBean("rubroDao");
		List<RubroBean> allRubros = dao.getAll();
		for(RubroBean rubro : allRubros) {
			if(rubro.getIdRubroPadre()==null)
				rubros.add(rubro);
		}
		this.labels = labels;
	}
	public Flujo parse(WorkbookCursor cursor) throws ParsingException {
		cursor.saveCheckpoint();
		BloqueFlujo parte = parseBloque(labels.flujo.PARTE, cursor);
		cursor.restoreToCheckpoint();
		if(parte==null) return null;
		cursor.saveCheckpoint();
		BloqueFlujo contraparte = parseBloque(labels.flujo.CONTRAPARTE, cursor);
		cursor.restoreToCheckpoint();
		if(contraparte==null) return null;
		BloqueFlujo total = parseBloque(labels.flujo.TOTAL, cursor);
		if(total==null) return null;
		
		Flujo flujo = new Flujo();
		flujo.setParte(parte);
		flujo.setContraparte(contraparte);
		flujo.setTotal(total);
		
		flujo.checkValid();
		return flujo;
	}
	private BloqueFlujo parseBloque(Label nombreFlujo, WorkbookCursor cursor){
		BloqueFlujo bloqueFlujo = new BloqueFlujo();
		//Busco el flujo
		if(!seekFlujo(cursor, nombreFlujo)) return null;
		
		//Cargo los items
		ItemFlujo item;
		while((item=crearItem(cursor))!=null) {
			bloqueFlujo.addItem(item);
		}
		return bloqueFlujo;
	}

	/*
	 * Privadas
	 */


	
	private boolean seekFlujo(WorkbookCursor cursor, Label nombreFlujo) {
		while(cursor.hasNext()) {
			Cell[] row = cursor.nextRow();
			if(esTituloDeFlujo(row, nombreFlujo)) {
				//Posible indicador de rubro encontrado!
				cursor.saveCheckpoint();
				//saltea hasta dos filas vacias
				if(
						!ParseUtils.skipIfNextIsEmpty(cursor) ||
						!ParseUtils.skipIfNextIsEmpty(cursor)
				) {
					cursor.restoreToCheckpoint();
					return false;
				}
				//Espera encabezado de lista
				row = cursor.nextRow();
				if(esEncabezado(row)) {
					cursor.clearCheckpoint();
					return true;
				} else {
					//admite una linea no vacia mas
					if(cursor.hasNext()) {
						row = cursor.nextRow();
						if(esEncabezado(row)) {
							cursor.clearCheckpoint();
							return true;
						} else {
							cursor.restoreToCheckpoint();
							//sigo buscando
							continue;
						}
					} else {
						//eof!?
						cursor.restoreToCheckpoint();
					}
				}
			}
		}
		return false;
	}
	private boolean esTituloDeFlujo(Cell[] row, Label tituloFlujo) {
		return ParseUtils.find(tituloFlujo, row)!=null;
	}
	 
	private boolean esEncabezado(Cell[] row) {
		FieldMatching matching = FieldMatching.create(row, config.LabelsDePeriodos());
		if(matching==null) return false;
		else {
			config.setMatching(matching);
			return true;
		}
	}
	/*
	 * Construye el mapping entre periodos y celdas. No consume
	 * cursor. 
	 */
	private Map<Label, Cell> buildPeriodMap(WorkbookCursor cursor) {
		Cell[] row = cursor.inspectNext();
		if(row==null) return null;
		return config.getMatching().applyNonStrict(row);
	}
		

	protected ItemFlujo crearItem(WorkbookCursor cursor) {
		ItemFlujo item = new ItemFlujo();
		
		Map<Label, Cell> periodos = buildPeriodMap(cursor);
		if(periodos==null) return null; //No hay un item aca

		Cell[] row = cursor.nextRow();
		//Intento ubicar el titulo del rubro
		Cell tituloRubro = null;
		for(RubroBean rubro : this.rubros) {
			tituloRubro = ParseUtils.find(config.getLabelDeRubro(rubro), row);
			if(tituloRubro!=null) {
				item.setRubro(rubro);
				break;
			}
		}
		if(tituloRubro==null) return null;
		
		//parseo los periodos
		for(Label label : config.LabelsDePeriodos()) {
			Cell periodo = periodos.get(label);
			if(ParseUtils.isNumeric(periodo)) {
				item.add(config.getMatching().nameForField(label), ((NumberCell)periodos.get(label)).getValue());
			} else {
				if(ParseUtils.isEmpty(periodo)) {
					item.add(config.getMatching().nameForField(label), 0.0);
				} else {
					return null;
				}
			}
		}

		return item;
	}
}
