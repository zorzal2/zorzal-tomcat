package com.fontar.proyecto.presupuesto.excel.parser;

import com.fontar.proyecto.presupuesto.excel.parser.flujo.cf_general.FlujoCFGeneralParser;
import com.fontar.proyecto.presupuesto.excel.parser.flujo.cf_general.cf.FlujoCFParser;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.excel.parser.plan.PlanParser;


public class FormularioCFParser extends FormularioCFGeneralParser {

	@Override
	protected PlanParser createPlanParser() {
		return new PlanParser(Labels.CF.plan);
	}
	@Override
	protected FlujoCFGeneralParser createFlujoParser() {
		return new FlujoCFParser();
	}
}
