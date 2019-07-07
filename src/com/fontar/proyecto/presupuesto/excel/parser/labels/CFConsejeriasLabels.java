package com.fontar.proyecto.presupuesto.excel.parser.labels;

import com.pragma.excel.parser.util.Label;

public class CFConsejeriasLabels extends DefaultLabels {
	public static CFConsejeriasLabels instance = new CFConsejeriasLabels();
	
	public CFConsejeriasLabels() {
		super();
		flujo = new Flujo();
		plan = new Plan();
		itemConsejeroTecnologico = new ItemConsejeroTecnologico();
	}

	public static class Flujo extends DefaultLabels.Flujo {
		Flujo() {
			TOTAL = new Label("CUADRO 8*");
		}
	}
	public class Plan extends DefaultLabels.Plan {
		Plan() {
			ETAPAS = new Label("CUADRO 9:*", "DESCRIPCION DE LAS ETAPAS");
			ACTIVIDADES = new Label("CUADRO 10:*", "DESCRIPCION DE LAS ACTIVIDADES*");
		}
	}
	public static class ItemConsejeroTecnologico {
		public Label NOMBRE = new Label("nombre", "nombre y apellido*");
		public Label PROFESION = new Label("profesi?n u oficio*");
		public Label ID_TRIBUTARIA = new Label("CUIL / CUIT*", "CUIT / CUIL*");
		public Label FUNCION = new Label("FUNCI?N EN EL PROYECTO*");
		public Label COSTO_MENSUAL = new Label("SUELDO MENSUAL ASIGNADO*");
		public Label DEDICACION = new Label("% DEDICACI?N AL PROYECTO*");
		public Label PARTICIPACION = new Label("MESES DE PARTICIPACI?N EN EL PROY*");
		public Label COSTO_TOTAL = new Label("costo total");
	}	
	public ItemConsejeroTecnologico itemConsejeroTecnologico;
}
