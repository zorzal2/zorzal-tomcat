package com.fontar.proyecto.datos.excel.parser.parsers;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import jxl.Cell;

import com.pragma.excel.exception.ParsingException;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;
import com.pragma.excel.parser.util.cusor.WorkbookCursor;
import com.pragma.excel.parser.util.pattern.ParsingProcess;
import com.pragma.excel.parser.util.pattern.PatternLibrary;
import com.pragma.excel.parser.util.pattern.PatternParser;


public class SeccionParser {

	public Map<String, Object> parse(WorkbookCursor cursor, String titulo) throws ParsingException {

		Map<String, Object> ret = new Hashtable<String, Object>();
		
		PatternParser pattern = PatternLibrary.composition.of(
			//Lineas vacias opcionales
			PatternLibrary.zeroOrMoreBlanks,
			//Titulo
			PatternLibrary.title(new Label(titulo)),
			//Lineas vacias opcionales
			PatternLibrary.zeroOrMoreBlanks,
			//varios pares campo-valor
			PatternLibrary.zeroOrMore(PatternLibrary.nonBlank).named("pares")
		);
		
		//Busco el pattern
		ParsingProcess parsingProcess = new ParsingProcess(pattern);
		parsingProcess.find(cursor);
		List<Cell[]> pairs = parsingProcess.getNamedRowset("pares"); 
		if(pairs==null) return null;
		
		for(Cell[] pairRow : pairs) {
			String nombreCampo = null;
			Object valorCampo = null;
			for(Cell cell : pairRow) {
				if(!ParseUtils.isEmpty(cell)) {
					if(nombreCampo==null) nombreCampo = cell.getContents();
					else {
						valorCampo = ParseUtils.getCellValue(cell);
						break;
					}
				}
			}
			//Tengo el par
			if(valorCampo!=null && nombreCampo!=null) ret.put(nombreCampo, valorCampo);
		}
		return ret;
	}
}
