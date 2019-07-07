package com.fontar.proyecto.presupuesto.excel.parser.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.DateCell;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.IllegalFormatException;
import com.pragma.excel.exception.ParsingException;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.Field;
import com.pragma.excel.parser.util.FieldMatching;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;
import com.pragma.excel.parser.util.cusor.WorkbookCursor;
/**
 * Intenta crear el PresupuestoRubroGeneralBean leyendo el cursor dado.
 * @author llobeto
 *
 */
public abstract class BaseRubroParser implements RubroParser {

	private final RubroBean rubro;
	private FieldMatching matching;

	/*
	 * Constructores
	 */
	public BaseRubroParser(RubroBean rubro) {
		this.rubro = rubro;
	}

	/*
	 * Abstractas
	 */
	/**
	 * Devuelve la lista de campos como una coleccion de Field. 
	 * @return
	 */
	protected abstract Collection<Field> getCampos();

	/**
	 * Construye un item a partir de las celdas que contienen los
	 * valores de los campos. Chequea que esten los obligatorios. 
	 * Devuelve el item o null si no hay un item en la fila.
	 * Levanta una excepcion si no se cumple el formato del item.
	 * @param map
	 * @return
	 * @throws IllegalFormatException
	 */
	protected abstract PresupuestoItemBean crearItem(Map<Label, Cell> map) throws IllegalFormatException;
	/**
	 * Chequea la validez de los datos del item. Dispara una exepcion
	 * si no se cumplen las condiciones para que el item sea valido.
	 * Devuelve el item que recibe o, tal vez, uno distinto con valores
	 * corregidos. 
	 * @param item
	 * @throws ValidationException
	 */
	protected abstract PresupuestoItemBean checkItem(PresupuestoItemBean item) throws ValidationException;

	/*
	 * Publicas
	 */
	public PresupuestoRubroGeneralBean parse(WorkbookCursor cursor) throws ParsingException {
		cursor.saveCheckpoint();
		
		//Busco el rubro
		if(!seekRubro(cursor)) {
			//No existe el rubro
			cursor.restoreToCheckpoint();
			return null;
		}
		
		PresupuestoRubroGeneralBean presupuestoRubro = new PresupuestoRubroGeneralBean();
		presupuestoRubro.setRubro(this.getRubro());
		
		//Cargo los items
		List<PresupuestoItemBean> items = new ArrayList<PresupuestoItemBean>();
		PresupuestoItemBean item;
		double parte=0.0, contraparte=0.0;
		try {
			while((item=tryCreateItem(cursor))!=null) {
				contraparte+=item.getContraparte();
				parte+=item.getParte();
				items.add(item);
			}
		} catch (IllegalFormatException e) {
			cursor.restoreToCheckpoint();
			throw e;
		}
		presupuestoRubro.setItems(items);
		presupuestoRubro.setMontoContraparte(contraparte);
		presupuestoRubro.setMontoParte(parte);

		cursor.clearCheckpoint();
		return presupuestoRubro;
	}
	/*
	 * Privadas
	 */
	/**
	 * Se posiciona en el primer item del rubro. Adicionalmente setea
	 * el matching que deben respetar los items.
	 */
	private boolean seekRubro(WorkbookCursor cursor) {
		Label labelDeRubro = getLabelDeRubro(getRubro());
		while(cursor.hasNext()) {
			Cell[] row = cursor.nextRow();
			if(esTituloDeRubro(row, labelDeRubro)) {
				//Posible indicador de rubro encontrado!
				cursor.saveCheckpoint();
				//saltea las filas vacias
				if(!ParseUtils.skipBlanks(cursor)) {
					cursor.restoreToCheckpoint();
					return false;
				}
				//Espera encabezado de lista
				row = cursor.nextRow();
				if(esEncabezado(row)) {
					//Admito una fila vacia mas
					if(!ParseUtils.skipIfNextIsEmpty(cursor)) {
						cursor.restoreToCheckpoint();
						return false;
					}
					cursor.clearCheckpoint();
					return true;
				} else {
					//admite una linea no vacia mas
					if(cursor.hasNext()) {
						row = cursor.nextRow();
						if(esEncabezado(row)) {
							//Admito una fila vacia mas
							if(!ParseUtils.skipIfNextIsEmpty(cursor)) {
								cursor.restoreToCheckpoint();
								return false;
							}							
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
	private boolean esTituloDeRubro(Cell[] row, Label tituloRubro) {
		return ParseUtils.find(tituloRubro, row)!=null;
	}
	private boolean esEncabezado(Cell[] row) {
		matching = FieldMatching.create(row, getCampos());
		return matching!=null;
	}
	private Label getLabelDeRubro(RubroBean rubro) {
		return new Label(rubro.getCodigoLargo().split("\\|"));
	}
	private Map<Label, Cell> buildItemMap(WorkbookCursor cursor) {
		if(!cursor.hasNext()) return null;
		Cell[] row = cursor.nextRow();
		return matching.applyStrict(row);
	}
	private PresupuestoItemBean tryCreateItem(WorkbookCursor cursor) throws ParsingException {
		Map<Label, Cell> cellMap = buildItemMap(cursor);
		if(cellMap==null) return null;
		//chequeo que no me estoy encontrando con una fila de subtotal
		for (Cell cell : cellMap.values()) {
			if(Labels.Default.SUBTOTAL.matches(cell.getContents())) return null;
		}
		PresupuestoItemBean item = crearItem(cellMap);
		if(item==null) return null;
		return checkItem(item);
	}

	public RubroBean getRubro() {
		return rubro;
	}
	
	/*
	 * Auxiliares
	 */
	protected Double numberOrNull(Cell cell) {
		if(ParseUtils.isNumeric(cell))
			return (Double) ParseUtils.getCellValue(cell); 
		else
			return null;
	}
	protected String textOrNull(Cell cell) {
		if(ParseUtils.isEmpty(cell))
			return null;
		else
			return cell.getContents();
	}
	protected Date dateOrNull(Cell cell) {
		if(ParseUtils.isDate(cell))
			return ParseUtils.getDate((DateCell)cell);
		else
			return null;
	}
	/**
	 * Validacion minima para todos los items de rubro.
	 * @param item
	 * @throws ValidationException
	 */
	protected void baseItemCheck(PresupuestoItemBean item) throws ValidationException {
		obligatorios(
				item.getParte(),
				item.getContraparte(),
				item.getTotal()
		);
		if(item.getParte()<0 || item.getContraparte()<0 || item.getTotal()<=0)
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.valoresNegativos(item, getRubro()));
		if(!ParseUtils.similar(item.getParte()+item.getContraparte(), item.getTotal()))
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.totalDifiereDePartes(item, getRubro()));
	}
	/**
	 * Validacion de obligatoriedad
	 * @param objects
	 * @throws ValidationException
	 */
	protected void obligatorios(Object...objects) throws ValidationException {
		for(Object object : objects) 
			if(object==null)
				throw new ValidationException(PresupuestoMessages.presupuestoRubro.faltanCampos(getRubro()));
	}
}
