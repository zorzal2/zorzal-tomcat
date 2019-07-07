package com.fontar.proyecto.presupuesto.excel.parser.flujo.cf_general.cf;

import com.fontar.proyecto.presupuesto.excel.parser.flujo.cf_general.FlujoCFGeneralParser;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.pragma.excel.parser.util.Label;

public class FlujoCFParser extends FlujoCFGeneralParser {
	@Override
	protected Label tituloBloque() {
		return Labels.CF.flujo.TOTAL;
	}
}
