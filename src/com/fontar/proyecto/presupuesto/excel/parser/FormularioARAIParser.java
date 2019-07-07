package com.fontar.proyecto.presupuesto.excel.parser;

import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.arai.RubroARAIParsers;
import com.fontar.proyecto.presupuesto.excel.parser.presupuesto.PresupuestoParserSettings;




public class FormularioARAIParser extends FormularioANRGeneralParser {

	public FormularioARAIParser() {
		super(Labels.ARAI);
	}
	protected PresupuestoParserSettings createSettings() {
		PresupuestoParserSettings settings = super.createSettings();
		settings.setRubroParserSet(RubroARAIParsers.instance);
		return settings;
	}
}
