package com.fontar.proyecto.presupuesto.excel.parser.flujo.cf_general.cf_consejerias;

import com.fontar.proyecto.presupuesto.excel.parser.flujo.cf_general.cf.FlujoCFParser;
import com.fontar.proyecto.presupuesto.excel.parser.labels.Labels;
import com.pragma.excel.parser.util.Label;

public class FlujoCFConsejeriasParser extends FlujoCFParser {

	@Override
	protected Label tituloBloque() {
		return Labels.CFConsejerias.flujo.TOTAL;
	}
}
