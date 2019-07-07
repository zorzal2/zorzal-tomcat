package com.fontar.proyecto.presupuesto.excel.parser.parsers.cf_general;

import com.fontar.data.impl.domain.codes.rubro.TipoRubro;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.RubroParsers;

public class RubroCFGeneralParsers extends RubroParsers {
	public static final RubroCFGeneralParsers instance = new RubroCFGeneralParsers(); 
	@Override
	protected void addParsers() {
		addParser(TipoRubro.BIEN, RubroBienParser.class);
		addParser(TipoRubro.CONSULTOR, RubroConsultorParser.class);
		addParser(TipoRubro.CONSEJERO_TECNOLOGICO, RubroConsejeroTecnologicoParser.class);
		addParser(TipoRubro.INSUMO, RubroInsumoParser.class);
		addParser(TipoRubro.RECURSO_HUMANO_ADICIONAL, RubroRecursoHumanoAdicionalParser.class);
		addParser(TipoRubro.RECURSO_HUMANO_PROPIO, RubroRecursoHumanoPropioParser.class);
	}
}
