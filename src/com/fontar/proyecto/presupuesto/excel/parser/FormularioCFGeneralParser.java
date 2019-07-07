package com.fontar.proyecto.presupuesto.excel.parser;

import com.fontar.proyecto.presupuesto.excel.parser.flujo.cf_general.FlujoCFGeneralParser;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.cf_general.RubroCFGeneralParsers;
import com.fontar.proyecto.presupuesto.excel.parser.plan.PlanParser;
import com.fontar.proyecto.presupuesto.excel.parser.presupuesto.BasePresupuestoParser;
import com.fontar.proyecto.presupuesto.excel.parser.presupuesto.PresupuestoParserSettings;

public abstract class FormularioCFGeneralParser extends BasePresupuestoParser {

	protected PresupuestoParserSettings createSettings() {
		PresupuestoParserSettings settings = new PresupuestoParserSettings();
		settings.flujoEsOpcional(false);
		settings.planEsOpcional(false);
		settings.setFlujoParser(createFlujoParser());
		settings.setPlanParser(createPlanParser());
		settings.setRubroParserSet(RubroCFGeneralParsers.instance);
		settings.tieneFlujo(true);
		settings.tienePlan(true);
		return settings;
	}
	protected abstract PlanParser createPlanParser();
	protected abstract FlujoCFGeneralParser createFlujoParser();
}
