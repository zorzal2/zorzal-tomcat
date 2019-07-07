package com.fontar.proyecto.datos.excel.parser;

import java.util.Collection;

import jxl.Workbook;

import com.fontar.proyecto.datos.excel.parser.parsers.ProyectoFilasParser;
import com.fontar.proyecto.datos.modelo.ProyectoInfo;
import com.pragma.excel.exception.ParsingException;
import com.pragma.excel.parser.util.cusor.WorkbookCursor;

public class FormularioAParser {
	public Collection<ProyectoInfo> parseProyectosDatos(Workbook workbook) throws ParsingException {
		WorkbookCursor cursor = new WorkbookCursor(workbook);
		return  new ProyectoFilasParser().parseProyectos(cursor);
	}
}
