package com.fontar.proyecto.presupuesto.excel.parser.parsers.consejerias;

import com.fontar.data.impl.domain.codes.rubro.TipoRubro;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.RubroParsers;

public class RubroConsejeriasParsers extends RubroParsers {
	public static final RubroConsejeriasParsers instance = new RubroConsejeriasParsers(); 
	@Override
	protected void addParsers() {
		addParser(TipoRubro.DIRECTOR_EXPERTO, RubroDirectorExpertoParser.class);
		addParser(TipoRubro.CONSEJERO_TECNOLOGICO, RubroConsejeroTecnologicoParser.class);
		addParser(TipoRubro.CANON_INSTITUCIONAL, RubroCanonInstitucionalParser.class);
		addParser(TipoRubro.BIEN, RubroOtrosGastosParser.class);
	}

}
