package com.fontar.proyecto.presupuesto.excel.parser.labels;

import com.pragma.excel.parser.util.Label;

public class PatenteLabels {
	public static PatenteLabels instance = new PatenteLabels();
	
	public Label TOTAL_DEL_PROYECTO = new Label("COSTO TOTAL DEL PROYECTO");
	public Label TOTAL_CYS = new Label("Total Consultoría / Honorarios");
	public Label TOTAL_OTROS_COSTOS = new Label("Total Otros Costos");
}