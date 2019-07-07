package com.fontar.proyecto.pac.excel.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import jxl.Cell;
import jxl.Workbook;

import com.fontar.data.api.dao.TipoAdquisicionDAO;
import com.fontar.data.api.dao.proyecto.RubroDAO;
import com.fontar.data.impl.domain.bean.PacBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.TipoAdquisicionBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem;
import com.fontar.proyecto.pac.message.PacMessages;
import com.pragma.excel.exception.ParsingException;
import com.pragma.excel.parser.util.Field;
import com.pragma.excel.parser.util.FieldMatching;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;
import com.pragma.excel.parser.util.cusor.WorkbookCursor;
import com.pragma.excel.parser.util.cusor.exception.InvalidSheetNameException;
import com.pragma.excel.parser.util.pattern.ParsingProcess;
import com.pragma.excel.parser.util.pattern.PatternLibrary;
import com.pragma.excel.parser.util.pattern.PatternParser;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;

/**
 * El PAC se ingresa en el sistema a partir de una planilla Excel.
 * Este objeto permite tratar la importacion de los datos de PAC. 
 * 
 * @see com.fontar.data.impl.domain.bean.PacBean
 * @see com.fontar.bus.api.proyecto.pac.AdministrarPACServicio
 */
public class PacParser {
	private ProyectoBean proyecto;
	private ProyectoPresupuestoBean presupuesto;
	public List<PacBean> parse(Workbook workbook, ProyectoBean proyecto) throws ParsingException {
		this.proyecto = proyecto;
		presupuesto = proyecto.getProyectoPresupuesto();
		
		WorkbookCursor cursor = new WorkbookCursor(workbook);
		try {
			cursor.goToSheet(PacLabels.SHEET_NAME);
		} catch(InvalidSheetNameException exception) {
			//El archivo no tiene solapa de PAC
			throw new PacValidationException(PacMessages.noSeEncuentraPac());
		}
		String sheetName = workbook.getSheets()[cursor.getPosition().getSheetIndex()].getName();
		//Busco los campos
		PatternParser pattern = PatternLibrary.composition.of(
				//Encabezado
				PatternLibrary.header.havingFields(
						new Field(PacLabels.ITEM, true),
						new Field(PacLabels.DESCRIPCION, true),
						new Field(PacLabels.ETAPA, false),
						new Field(PacLabels.RUBRO, false),
						new Field(PacLabels.MONTO_ESTIMADO, false),
						new Field(PacLabels.ADQUISICION, false),
						new Field(PacLabels.FECHA_ESTIMADA, false)
				).named("Encabezado"),
				//Items
				PatternLibrary.itemsMatchingStrict("Encabezado").named("Items")
		);
		ParsingProcess process = new ParsingProcess(pattern);
		process.find(cursor);
		if(process.getResult()==null) {
			//Formato no valido
			throw new PacValidationException(PacMessages.pacInvalido(sheetName));
		}
		
		FieldMatching matching = (FieldMatching) process.getVar("Encabezado");
		List<Cell[]> items = process.getNamedRowset("Items");
		List<PacBean> ret = new ArrayList<PacBean>();
		ParsingException parsingException = new ParsingException();
		for(Cell[] row : items) {
			try {
				ret.add(tryCreateBean(row, matching));
			} catch(ParsingException e) {
				//Guardo el error y continuo
				parsingException.appendMessages(e.getMessages());
			}
		}
		if(parsingException.hasMessages()) throw parsingException;
		
		validate(ret);
		return  ret;
	}


	/*
	 * Validacion:
	 * - Los montos estimados deben sumar el monto fontar del presupuesto
	 * - Los codigos de items no pueden repetirse. 
	 */
	private void validate(Collection<PacBean> ret) throws PacValidationException {
		Set<Long> items = new HashSet<Long>();
		BigDecimal montoTotal = BigDecimal.ZERO;
		for(PacBean bean : ret) {
			items.add(bean.getItem());
			montoTotal = montoTotal.add(bean.getMontoFontar());
		}
		if(items.size()!=ret.size())
			throw new PacValidationException(PacMessages.itemsRepetidos());
		if(!ParseUtils.similar(montoTotal.doubleValue(), presupuesto.getMontoSolicitado().doubleValue()))
			throw new PacValidationException(
				PacMessages.montoEstimadoNoValido(
					StringUtil.formatMoneyForPresentation(presupuesto.getMontoSolicitado())
				)
			);
	}

	private PacBean tryCreateBean(Cell[] row, FieldMatching matching) throws PacValidationException {
		Map<Label, Cell> itemAsMap = matching.applyNonStrict(row);
		PacBean bean = new PacBean();
		String itemId;
		{	//item: obligatorio y con un valor numerico.
			Cell cell = itemAsMap.get(PacLabels.ITEM);
			if(!ParseUtils.isNumeric(cell))
				throw new PacValidationException(PacMessages.itemNoNumerco(itemAsMap.get(PacLabels.DESCRIPCION).getContents()));
			itemId = cell.getContents();
			Double cellValue = (Double)ParseUtils.getCellValue(cell);
			bean.setItem(cellValue.longValue());
		}
		{	//descripcion: obligatorio y con un valor de cadena.
			Cell cell = itemAsMap.get(PacLabels.DESCRIPCION);
			if(cell.getContents().equals(""))
				throw new PacValidationException(PacMessages.faltanCampos(itemId));
			bean.setDescripcion(cell.getContents());
		}
		{	//etapa: obligatorio y coincidente con una etapa del presupuesto.
			Cell cell = itemAsMap.get(PacLabels.ETAPA);
			String etapa = cell.getContents();
			if(etapa.equals(""))
				throw new PacValidationException(PacMessages.faltanCampos(itemId));
			//Verifico que esta en el presupuesto.
			boolean valid = false;
			for(EtapaBean etapaBean : presupuesto.getEtapas()) {
				if(etapaBean.getNombre().equalsIgnoreCase(etapa)) {
					valid = true;
					break;
				}
			}
			if(!valid) 
				throw new PacValidationException(PacMessages.etapaInvalida(itemId));
			bean.setEtapa(etapa);
		}
		{//rubro: obligatorio y debe ser un codigo valido de rubro
			Cell cell = itemAsMap.get(PacLabels.RUBRO);
			String rubro = cell.getContents();
			if(rubro.equals(""))
				throw new PacValidationException(PacMessages.faltanCampos(itemId));
			RubroBean rubroBean = getRubroByName(rubro);
			if(rubroBean==null)
				throw new PacValidationException(PacMessages.rubroInvalido(itemId));
			bean.setRubro(rubroBean);
			bean.setIdRubro(rubroBean.getId());
		}
		{	//monto estimado: obligatorio y con un valor numerico positivo.
			Cell cell = itemAsMap.get(PacLabels.MONTO_ESTIMADO);
			if(!ParseUtils.isNumeric(cell))
				throw new PacValidationException(PacMessages.montoEstimadoNoNumerico(itemId));
			Double cellValue = (Double)ParseUtils.getCellValue(cell);
			if(cellValue<=0)
				throw new PacValidationException(PacMessages.montoEstimadoNoNumerico(itemId));
			bean.setMontoFontar(new BigDecimal(cellValue));
		}
		{//adquisicion: obligatorio y debe ser un codigo valido de tipo de adquisicion
			Cell cell = itemAsMap.get(PacLabels.ADQUISICION);
			String adquisicion = cell.getContents();
			if(adquisicion.equals(""))
				throw new PacValidationException(PacMessages.faltanCampos(itemId));
			TipoAdquisicionBean adquisicionBean = getTipoAdquisicionByName(adquisicion);
			if(adquisicionBean==null)
				throw new PacValidationException(PacMessages.tipoDeAdquisicionNoValido(itemId));
			bean.setTipoAdquisicion(adquisicionBean);
			bean.setIdTipoAdquisicion(adquisicionBean.getId());
		}
		{	//fecha estimada: obligatorio y con una fecha.
			Cell cell = itemAsMap.get(PacLabels.FECHA_ESTIMADA);
			if(!ParseUtils.isDate(cell))
				throw new PacValidationException(PacMessages.faltanCampos(itemId));
			bean.setFecha((Date) ParseUtils.getCellValue(cell));
		}
		//Le pongo el estado default
		bean.setCodigoEstado(EstadoPacItem.PENDIENTE_DE_COMPRA);
		bean.setFechaEstado(new Date());
		bean.setProyecto(proyecto);
		bean.setIdProyecto(proyecto.getId());
		return bean;
	}
	
	private RubroBean getRubroByName(String name) {
		if(allRubros==null) loadRubros();
		for (Entry<Label, RubroBean> rubroLabel : allRubros.entrySet()) {
			if(rubroLabel.getKey().matches(name)) return rubroLabel.getValue(); 
		}
		return null;
	}
	private List<TipoAdquisicionBean> allAdquisiciones = null;
	private TipoAdquisicionBean getTipoAdquisicionByName(String name) {
		if(allAdquisiciones==null) {
			TipoAdquisicionDAO dao = (TipoAdquisicionDAO)ContextUtil.getBean("tipoAdquisicionDao");
			allAdquisiciones = dao.getAll();
		}
		for (TipoAdquisicionBean adquisicion : allAdquisiciones) {
			if(adquisicion.getCodigo().equalsIgnoreCase(name)) return adquisicion;
		}
		return null;
	}
	
	private Map<Label, RubroBean> allRubros = null;
	private void loadRubros() {
		//Mapa de abreviaturas de rubros
		allRubros = new Hashtable<Label, RubroBean>();
		allRubros.put(PacLabels.BIENES, findRubroByCodigo("bienes.maquinarias"));
		allRubros.put(PacLabels.OBRAS, findRubroByCodigo("bienes.infraestructura"));
		allRubros.put(PacLabels.CONS, findRubroByCodigo("cys"));
		allRubros.put(PacLabels.MAT, findRubroByCodigo("insumos"));
		allRubros.put(PacLabels.OTROS, findRubroByCodigo("otrosCostos"));
	}
	
	private RubroDAO dao = (RubroDAO)ContextUtil.getBean("rubroDao");
	private RubroBean findRubroByCodigo(String codigo) {
		List<RubroBean> list = dao.findByCodigo(codigo);
		if(list.size()!=1) return null;
		return list.get(0);
	}
}
