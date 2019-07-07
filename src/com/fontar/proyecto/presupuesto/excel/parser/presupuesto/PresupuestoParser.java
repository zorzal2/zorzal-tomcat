package com.fontar.proyecto.presupuesto.excel.parser.presupuesto;

import jxl.Workbook;

import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.pragma.excel.exception.ParsingException;

public interface PresupuestoParser {
	
	public abstract ProyectoPresupuestoBean parse(Workbook workbook) throws ParsingException;}
