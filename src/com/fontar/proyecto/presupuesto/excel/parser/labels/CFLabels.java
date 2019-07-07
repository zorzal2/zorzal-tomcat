package com.fontar.proyecto.presupuesto.excel.parser.labels;

import com.pragma.excel.parser.util.Label;

public class CFLabels extends DefaultLabels {
	public static CFLabels instance = new CFLabels();

	public CFLabels() {
		super();
		flujo = new Flujo();
		plan = new Plan();
	}
	
	public static class Flujo extends DefaultLabels.Flujo {
		Flujo() {
			TOTAL = new Label("CUADRO 13.9*");
		}
	}
	public class Plan extends DefaultLabels.Plan {
		Plan() {
			ETAPAS = new Label("CUADRO 13.12*", "DESCRIPCION DE LAS ETAPAS");
			ACTIVIDADES = new Label("CUADRO 13.13*", "DESCRIPCION DE LAS ACTIVIDADES*");
		}
	}
}
