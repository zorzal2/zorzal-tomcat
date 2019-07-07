package com.fontar.proyecto.presupuesto.excel.parser.labels;

import com.pragma.excel.parser.util.Label;

public class ANRLabels extends DefaultLabels {
	public static ANRLabels instance = new ANRLabels();

	public ANRLabels() {
		super();
		flujo = new Flujo();
		plan = new Plan();
	}
	
	public static class Flujo extends DefaultLabels.Flujo {
		Flujo() {
			PARTE = new Label("CUADRO 11.7*", "CUADRO 9.7*");
			CONTRAPARTE = new Label("CUADRO 11.8*", "CUADRO 9.8*");
			TOTAL = new Label("CUADRO 11.9*", "CUADRO 9.9*");
		}
	}
	public class Plan extends DefaultLabels.Plan {
		Plan() {
			ETAPAS = new Label("CUADRO 11.10*","CUADRO 9.10*", "DESCRIPCION DE LAS ETAPAS");
			ACTIVIDADES = new Label("CUADRO 11.11*", "CUADRO 9.11*", "DESCRIPCION DE LAS ACTIVIDADES*");
		}
	}
}
