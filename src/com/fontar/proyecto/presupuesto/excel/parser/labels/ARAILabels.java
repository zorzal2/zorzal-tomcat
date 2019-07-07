package com.fontar.proyecto.presupuesto.excel.parser.labels;

import com.pragma.excel.parser.util.Label;

public class ARAILabels extends DefaultLabels {
	public static ARAILabels instance = new ARAILabels();

	public ARAILabels() {
		super();
		flujo = new Flujo();
		plan = new Plan();
	}
	
	public static class Flujo extends DefaultLabels.Flujo {
		Flujo() {
			PARTE = new Label("CUADRO 9.7*");
			CONTRAPARTE = new Label("CUADRO 9.8*");
			TOTAL = new Label("CUADRO 9.9*");
		}
	}
	public class Plan extends DefaultLabels.Plan {
		Plan() {
			ETAPAS = new Label("CUADRO 9.10*", "DESCRIPCION DE LAS ETAPAS");
			ACTIVIDADES = new Label("CUADRO 9.11*", "DESCRIPCION DE LAS ACTIVIDADES*");
		}
	}
}
