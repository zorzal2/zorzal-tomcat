package com.fontar.proyecto.presupuesto.excel.parser.parsers.consejerias;

import java.util.ArrayList;
import java.util.List;

import jxl.Cell;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemDirectorExpertoBean;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.RubroParser;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.ParsingException;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;
import com.pragma.excel.parser.util.cusor.CursorPosition;
import com.pragma.excel.parser.util.cusor.WorkbookCursor;
import com.pragma.excel.parser.util.pattern.ParsingProcess;
import com.pragma.excel.parser.util.pattern.PatternLibrary;
import com.pragma.excel.parser.util.pattern.PatternParser;
import com.pragma.excel.parser.util.pattern.patterns.Header;
import com.pragma.excel.parser.util.pattern.patterns.Title;

public class RubroDirectorExpertoParser implements RubroParser {

	private final RubroBean rubro;

	/*
	 * Constructores
	 */
	public RubroDirectorExpertoParser(RubroBean rubro) {
		this.rubro = rubro;
	}

	/*
	 * Publicas
	 */
	public PresupuestoRubroGeneralBean parse(WorkbookCursor cursor) throws ParsingException {
		cursor.saveCheckpoint();
		
		//Busco el titulo del rubro
		Title rubroTitle = PatternLibrary.title(getLabelDeRubro(getRubro()));
		ParsingProcess process = new ParsingProcess(rubroTitle);
		List<Cell[]> titleRows = process.find(cursor);
		if(titleRows==null || titleRows.size()!=1) {
			//No existe el rubro
			cursor.restoreToCheckpoint();
			return null;
		}
		
		//Encontre un titulo candidato. Guardo el cursor a este punto
		CursorPosition cursorPosition = cursor.getPosition();
		
		//Busco el encabezado de la tabla de despliegue por empresas solo para control.
		Header encabezado = PatternLibrary.header.havingFields(
				Labels.Consejerias.itemDirectorExperto.EMPRESA,
				Labels.Consejerias.itemDirectorExperto.PARTE,
				Labels.Consejerias.itemDirectorExperto.CONTRAPARTE,
				Labels.Consejerias.itemDirectorExperto.TOTAL
		); 
		process = new ParsingProcess(encabezado);
		List<Cell[]> encabezadoRows = process.find(cursor);
		if(encabezadoRows==null || encabezadoRows.size()!=1) {
			//No existe el rubro
			cursor.restoreToCheckpoint();
			return null;
		}
		
		//Busco los totales
		Title totales = PatternLibrary.title(Labels.Consejerias.itemDirectorExperto.TOTAL); 
		process = new ParsingProcess(totales);
		List<Cell[]> totalesRows = process.find(cursor);
		if(totalesRows==null || totalesRows.size()!=1) {
			//No existe el rubro
			cursor.restoreToCheckpoint();
			return null;
		}
		
		//Busco los campos
		PatternParser pattern = PatternLibrary.composition.of(
				PatternLibrary.title(Labels.Consejerias.itemDirectorExperto.HONORARIO_MENSUAL), 
				PatternLibrary.title(Labels.Consejerias.itemDirectorExperto.DEDICACION), 
				PatternLibrary.title(Labels.Consejerias.itemDirectorExperto.HONORARIO_MENSUAL_ASIGNADO)
		).named("datos");
		
		process = new ParsingProcess(pattern);
		
		process.find(cursor);
		
		List<Cell[]> datos = process.getNamedRowset("datos");

		/*
		 * Chequeo que lo que encontre es lo que busco
		 *  - Todos los elementos deben estar en la misma hoja.
		 */
		if(cursor.getPosition().getSheetIndex()!=cursorPosition.getSheetIndex()) {
			/*
			Los elementos no estan en la misma hoja.
			Busco otra vez pero partiendo de la posicion del titulo del rubro
			*/
			cursor.clearCheckpoint();
			cursor.setPosition(cursorPosition);
			return parse(cursor);
		}
		
		
		//Creo el rubro con un item
		PresupuestoRubroGeneralBean presupuestoRubro = new PresupuestoRubroGeneralBean();
		presupuestoRubro.setRubro(this.getRubro());
		List<PresupuestoItemBean> items = new ArrayList<PresupuestoItemBean>();
		PresupuestoItemDirectorExpertoBean item = new PresupuestoItemDirectorExpertoBean();

		//Parseo totales
		if(totalesRows==null || totalesRows.size()!=1) {
			//No existe el rubro
			cursor.restoreToCheckpoint();
			return null;
		}
		parseTotales(totalesRows.get(0), item);

		//Parseo los valores del rubro
		if(datos==null || datos.size()!=3) {
			//No existe el rubro
			cursor.restoreToCheckpoint();
			return null;
		}

		item.setDedicacion(findNumber(datos.get(1)));
		item.setCostoMensual(findNumber(datos.get(2)));
		if(item.getTotal()!= null && item.getCostoMensual()!=null) {
			Double participacion = Math.rint(item.getTotal().doubleValue()/item.getCostoMensual().doubleValue());
			item.setParticipacion(participacion);
		} else {
			item.setParticipacion(null);			
		}
		
		item.setFuncion("");
		item.setIdentificacionTributaria("");
		item.setNombre("");
		item.setProfesion("");
		
		checkItem(item);

		items.add(item);
		presupuestoRubro.setItems(items);
		presupuestoRubro.setMontoContraparte(item.getContraparte());
		presupuestoRubro.setMontoParte(item.getParte());

		cursor.clearCheckpoint();
		return presupuestoRubro;
	}
	/*
	 * Privadas
	 */
	private Label getLabelDeRubro(RubroBean rubro) {
		return new Label(rubro.getCodigoLargo().split("\\|"));
	}
	private Double findNumber(Cell[] row) {
		for(Cell cell : row) {
			if(ParseUtils.isNumeric(cell)) return (Double) ParseUtils.getCellValue(cell);
		}
		return null;
	}
	private void parseTotales(Cell[] row, PresupuestoItemBean bean) {
		Double[] doubles = {null, null, null};
		int i = 0;
		for(Cell cell : row) {
			if(ParseUtils.isNumeric(cell)) {
				doubles[i] = (Double) ParseUtils.getCellValue(cell);
				i++;
				if(i==3) break;
			}
		}
		bean.setParte(doubles[0]);
		bean.setContraparte(doubles[1]);
		bean.setTotal(doubles[2]);
	}

	public RubroBean getRubro() {
		return rubro;
	}
	private void checkItem(PresupuestoItemDirectorExpertoBean item) throws ValidationException {
		if(
				item.getParte()==null || 
				item.getContraparte()==null || 
				item.getTotal()==null ||
				item.getTotal()==null ||
				item.getParticipacion()==null ||
				item.getDedicacion()==null
			) throw new ValidationException(PresupuestoMessages.presupuestoRubro.directorExperto.datosIncompletos(item));
		if(!ParseUtils.similar(item.getParte()+item.getContraparte(), item.getTotal()))
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.totalDifiereDePartes(item, getRubro()));
		if(item.getParte()<0 || item.getContraparte()<0)
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.valoresNegativos(item, getRubro()));
		if(!ParseUtils.similar(item.getTotal(), item.getCostoMensual() * item.getParticipacion() * item.getDedicacion()))
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.rh.totalIncoherente(item, getRubro()));
	}
}
