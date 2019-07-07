package com.fontar.proyecto.presupuesto.excel.parser;

import com.fontar.proyecto.presupuesto.excel.parser.flujo.cf_general.cf.FlujoCFParser;
import com.fontar.proyecto.presupuesto.excel.parser.flujo.cf_general.cf_consejerias.FlujoCFConsejeriasParser;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.excel.parser.plan.PlanParser;
import com.fontar.proyecto.presupuesto.excel.parser.presupuesto.PresupuestoParserSettings;


public class FormularioCFConsejeriasParser extends FormularioCFGeneralParser {

	@Override
	protected PresupuestoParserSettings createSettings() {
		PresupuestoParserSettings settings = super.createSettings();
		settings.flujoEsOpcional(true);
		return settings;
	}
	@Override
	protected PlanParser createPlanParser() {
		return new PlanParser(Labels.CFConsejerias.plan);
	}
	@Override
	protected FlujoCFParser createFlujoParser() {
		return new FlujoCFConsejeriasParser();
	}
}
