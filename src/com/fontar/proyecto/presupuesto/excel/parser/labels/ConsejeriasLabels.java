package com.fontar.proyecto.presupuesto.excel.parser.labels;

import com.pragma.excel.parser.util.Label;

public class ConsejeriasLabels {
	public static ConsejeriasLabels instance = new ConsejeriasLabels();

	public ConsejeriasLabels() {
		itemConsejeroTecnologico = new ItemConsejeroTecnologico();
		itemDirectorExperto = new ItemDirectorExperto();
		itemCanonInstitucional = new ItemCanonInstitucional();
		itemOtrosGastos = new ItemOtrosGastos();
	}
	public static class ItemConsejeroTecnologico {
		public Label TITULO = new Label("T?tulo");
		public Label NOMBRE = new Label("Nombre del Consejero");
		public Label ID_TRIBUTARIA = new Label("CUIT");
		public Label PARTICIPACION = new Label("Meses de participación");
		public Label COSTO_MENSUAL = new Label("Honorarios mensuales");
		public Label DEDICACION = new Label("% Dedicaci?n en el proyecto");
		public Label CATEGORIA = new Label("Categor?a");
		public Label PARTE = new Label("Aporte FONTAR");
		public Label CONTRAPARTE = new Label("Aporte Empresas");
		public Label COSTO_TOTAL = new Label("Honorarios Totales");
	}	
	public ItemConsejeroTecnologico itemConsejeroTecnologico;

	public static class ItemDirectorExperto {
		public Label EMPRESA = new Label("EMPRESA");
		public Label TOTAL = new Label("TOTAL");
		public Label PARTE = new Label("APORTE FONTAR");
		public Label CONTRAPARTE = new Label("APORTE EMPRESARIO");
		public Label HONORARIO_MENSUAL = new Label("Honorario Mensual");
		public Label DEDICACION = new Label("Porcentaje dedicaci?n");
		public Label HONORARIO_MENSUAL_ASIGNADO = new Label(
				"Honorario mensuales Asignado",//Error ortografico en el formulario
				"Honorario mensual Asignado",
				"Honorarios mensuales Asignados");
	}	
	public ItemDirectorExperto itemDirectorExperto;
	
	public static class ItemCanonInstitucional {
		public Label EMPRESA = new Label("EMPRESA");
		public Label PARTE = new Label("APORTE FONTAR");
		public Label CONTRAPARTE = new Label("APORTE EMPRESARIO");
		public Label TOTAL = new Label("TOTAL");
	}	
	public ItemCanonInstitucional itemCanonInstitucional;
	
	public static class ItemOtrosGastos {
		public Label DETALLE = new Label("DETALLE DE OTROS GASTOS");
		public Label COSTO_TOTAL = new Label("COSTO");
		public Label PARTE = new Label("Aporte FONTAR");
		public Label CONTRAPARTE = new Label("APORTE EMPRESARIO");
	}
	public ItemOtrosGastos itemOtrosGastos;
}