package com.fontar.proyecto.presupuesto.excel.parser.plan;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.DateCell;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.ActividadBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.proyecto.presupuesto.excel.parser.labels.DefaultLabels;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.IllegalFormatException;
import com.pragma.excel.exception.ParsingException;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.FieldMatching;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;
import com.pragma.excel.parser.util.cusor.WorkbookCursor;
import com.pragma.excel.parser.util.pattern.ParsingProcess;
import com.pragma.excel.parser.util.pattern.PatternLibrary;
import com.pragma.excel.parser.util.pattern.PatternParser;
import com.pragma.excel.parser.util.pattern.patterns.ItemsMatching;


public class PlanParser {
	LinkedHashMap<String, EtapaBean> etapas;
	DefaultLabels.Plan labels;
	
	public PlanParser(DefaultLabels.Plan labels) {
		this.labels = labels;
	}
	
	public Set<EtapaBean> parse(WorkbookCursor cursor) throws ParsingException {
		//Obtengo las etapas
		etapas = parseEtapas(cursor);
		if(etapas==null)
			throw new IllegalFormatException(PresupuestoMessages.plan.planInvalido());
		//Parseo las actividades
		parseActividades(cursor);
		return new LinkedHashSet<EtapaBean>(etapas.values());
	}
	private LinkedHashMap<String, EtapaBean> parseEtapas(WorkbookCursor cursor) throws ParsingException {

		ItemsMatching itemsPattern;
		
		//Estructura de la lista de etapas
		PatternParser pattern = PatternLibrary.composition.of(
			//Titulo
			PatternLibrary.title(labels.ETAPAS),
			//Lineas vacias opcionales
			PatternLibrary.zeroOrMoreBlanks,
			//Header
			PatternLibrary.header.havingFields(
					labels.COD_ETAPA,
					labels.DESCRIPCION,
					labels.INICIO,
					labels.FIN		
			).named("header de etapas"),
			//Items
			itemsPattern =
				PatternLibrary.itemsMatchingStrict("header de etapas")
		);
		
		//Busco el pattern
		List<Cell[]> rows = new ParsingProcess(pattern).find(cursor);
		if(rows==null) return null;
		
		LinkedHashMap<String, EtapaBean> etapas = new LinkedHashMap<String, EtapaBean>();
		ParsingException exception = new ParsingException();
		for(Map<Label, Cell> cellMap : itemsPattern.getMatchingResults()) {
			try {
				EtapaBean etapa = createEtapa(cellMap);
				if(etapa==null) break;
				etapas.put(etapa.getNombre(), etapa);
			} catch(ParsingException e) {
				//Capturo el error y continuo
				exception.appendMessages(e.getMessages());
			}
		}
		if(exception.hasMessages()) throw exception;
		return etapas;
	}
	private EtapaBean createEtapa(Map<Label, Cell> cellMap) throws IllegalFormatException, ValidationException {
		if(cellMap==null) return null;
		
		EtapaBean etapa = new EtapaBean();
		//nombre
		Cell nombre = cellMap.get(labels.COD_ETAPA);
		if(ParseUtils.isEmpty(nombre)) return null;
		etapa.setNombre(nombre.getContents());
		//descripcion
		Cell descripcion = cellMap.get(labels.DESCRIPCION);
		if(ParseUtils.isEmpty(descripcion)) return null;
		etapa.setDescripcion(descripcion.getContents());

		IllegalFormatException illegalFormatException = new IllegalFormatException();
		//inicio
		Cell inicio = cellMap.get(labels.INICIO);
		if(ParseUtils.isEmpty(inicio)) return null;
		if(!ParseUtils.isDate(inicio))
			illegalFormatException.appendMessage(PresupuestoMessages.plan.inicioDeEtapaInvalido(etapa));
		else etapa.setInicio(ParseUtils.getDate((DateCell)inicio));
		//fin
		Cell fin = cellMap.get(labels.FIN);
		if(ParseUtils.isEmpty(fin)) return null;
		if(!ParseUtils.isDate(fin))
			illegalFormatException.appendMessage(PresupuestoMessages.plan.finDeEtapaInvalido(etapa));
		else etapa.setFin(ParseUtils.getDate((DateCell)fin));
		
		if(illegalFormatException.hasMessages()) throw illegalFormatException;
		
		etapa.setActividades(new LinkedHashSet<ActividadBean>());
		
		if(etapa.getFin().before(etapa.getInicio())) 
			throw new ValidationException(PresupuestoMessages.plan.periodoDeEtapaInvalido(etapa));
		return etapa;
	}
	private List<ActividadBean> parseActividades(WorkbookCursor cursor) throws ParsingException {

		//Estructura de la lista de actividades
		PatternParser pattern = PatternLibrary.composition.of(
			//Titulo
			PatternLibrary.title(labels.ACTIVIDADES),
			//Lineas vacias opcionales
			PatternLibrary.zeroOrMoreBlanks,
			//Header
			PatternLibrary.header.havingFields(
					labels.COD_ETAPA,
					labels.COD_ACTIVIDAD,
					labels.DESCRIPCION,
					labels.INICIO,
					labels.FIN		
			).named("header de actividades"),
			//Items
			PatternLibrary.zeroOrMore(PatternLibrary.nonBlank).named("actividades")
		);
		
		//Busco el pattern
		ParsingProcess parsingProcess = new ParsingProcess(pattern);
		List<Cell[]> rows = parsingProcess.find(cursor);
		if(rows==null) return null;
		
		List<ActividadBean> actividades = new ArrayList<ActividadBean>();
		FieldMatching matching = (FieldMatching) parsingProcess.getVar("header de actividades");
		
		ParsingException exception = new ParsingException();
		for(Cell[] row : parsingProcess.getNamedRowset("actividades")) {
			try {
				Map<Label, Cell> cellMap = matching.applyNonStrict(row);
				//si no cumple con el formato basico asumo fin de la lista
				if(cellMap==null) break;
				ActividadBean actividad = createActividad(cellMap);
				//Puede haber items incompletos. los salteo
				if(actividad==null) continue;
	
				actividades.add(actividad);
			} catch(ParsingException e) {
				//Guardo el error y continuo
				exception.appendMessages(e.getMessages());
			}
		}
		if(exception.hasMessages()) throw exception;
		return actividades;
	}
	private ActividadBean createActividad(Map<Label, Cell> cellMap) throws IllegalFormatException, ValidationException {
		if(cellMap==null) return null;
		
		IllegalFormatException illegalFormatException = new IllegalFormatException();
		
		ActividadBean actividad = new ActividadBean();
		//nombre
		Cell nombre = cellMap.get(labels.COD_ACTIVIDAD);
		if(ParseUtils.isEmpty(nombre)) return null;
		actividad.setNombre(nombre.getContents());
		//descripcion
		Cell descripcion = cellMap.get(labels.DESCRIPCION);
		if(ParseUtils.isEmpty(descripcion)) return null;
		actividad.setDescripcion(descripcion.getContents());
		//etapa
		Cell codEtapa = cellMap.get(labels.COD_ETAPA);
		if(ParseUtils.isEmpty(codEtapa)) return null;
		EtapaBean etapaBean =etapas.get(codEtapa.getContents());
		if(etapaBean==null) 
			throw new ValidationException(PresupuestoMessages.plan.etapaInvalida(codEtapa.getContents(), actividad));
		actividad.setEtapa(etapaBean);
		//inicio
		Cell inicio = cellMap.get(labels.INICIO);
		if(ParseUtils.isEmpty(inicio)) return null;
		if(!ParseUtils.isDate(inicio)) {
			illegalFormatException.appendMessage(PresupuestoMessages.plan.inicioDeActividadInvalido(actividad));
		} else actividad.setInicio(ParseUtils.getDate((DateCell)inicio));
		//fin
		Cell fin = cellMap.get(labels.FIN);
		if(ParseUtils.isEmpty(fin)) return null;
		if(!ParseUtils.isDate(fin)) { 
			illegalFormatException.appendMessage(PresupuestoMessages.plan.finDeActividadInvalido(actividad));
		} else actividad.setFin(ParseUtils.getDate((DateCell)fin));
		
		if(illegalFormatException.hasMessages()) throw illegalFormatException;
		
		//Validaciones
		ValidationException validationException = new ValidationException();
		if(actividad.getFin().before(actividad.getInicio()))
			validationException.appendMessage(PresupuestoMessages.plan.periodoDeActividadInvalido(actividad));
		if(actividad.getInicio().before(actividad.getEtapa().getInicio())) 
			validationException.appendMessage(PresupuestoMessages.plan.periodoDeActividadNoCoincide(actividad));
		if(actividad.getFin().after(actividad.getEtapa().getFin())) 
			validationException.appendMessage(PresupuestoMessages.plan.periodoDeActividadNoCoincide(actividad));
		
		if(validationException.hasMessages()) throw validationException;
		
		etapaBean.addActividad(actividad);

		return actividad;
	}
}
