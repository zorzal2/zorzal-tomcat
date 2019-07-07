package com.fontar.proyecto.presupuesto.excel.parser;

import com.fontar.proyecto.presupuesto.excel.parser.parsers.consejerias.RubroConsejeriasParsers;
import com.fontar.proyecto.presupuesto.excel.parser.presupuesto.BasePresupuestoParser;
import com.fontar.proyecto.presupuesto.excel.parser.presupuesto.PresupuestoParserSettings;

public class FormularioConsejeriasParser extends BasePresupuestoParser {

	@Override
	protected PresupuestoParserSettings createSettings() {
		PresupuestoParserSettings settings = new PresupuestoParserSettings();
		settings.setRubroParserSet(RubroConsejeriasParsers.instance);
		settings.tieneFlujo(false);
		settings.tienePlan(false);
		return settings;
	}
}
