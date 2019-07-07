package com.fontar.proyecto.presupuesto.excel.parser;

import com.fontar.proyecto.presupuesto.excel.parser.flujo.anr.FlujoANRParser;
import com.fontar.proyecto.presupuesto.excel.parser.labels.DefaultLabels;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.anr.RubroANRParsers;
import com.fontar.proyecto.presupuesto.excel.parser.plan.PlanParser;
import com.fontar.proyecto.presupuesto.excel.parser.presupuesto.BasePresupuestoParser;
import com.fontar.proyecto.presupuesto.excel.parser.presupuesto.PresupuestoParserSettings;

public abstract class FormularioANRGeneralParser extends BasePresupuestoParser {

	DefaultLabels labelSet;
	
	public FormularioANRGeneralParser(DefaultLabels labelSet) {
		this.labelSet = labelSet;
	}

	protected PresupuestoParserSettings createSettings() {
		return createSettings(labelSet);
	}

	private PresupuestoParserSettings createSettings(DefaultLabels labelSet) {
		PresupuestoParserSettings settings = new PresupuestoParserSettings();
		settings.flujoEsOpcional(false);
		settings.planEsOpcional(false);
		settings.setFlujoParser(new FlujoANRParser(labelSet));
		settings.setPlanParser(new PlanParser(labelSet.plan));
		settings.setRubroParserSet(RubroANRParsers.instance);
		settings.tieneFlujo(true);
		settings.tienePlan(true);
		return settings;
	}
}
