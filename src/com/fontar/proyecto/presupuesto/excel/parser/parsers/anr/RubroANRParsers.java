package com.fontar.proyecto.presupuesto.excel.parser.parsers.anr;

import com.fontar.data.impl.domain.codes.rubro.TipoRubro;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.RubroParsers;

public class RubroANRParsers extends RubroParsers {

	public static final RubroANRParsers instance = new RubroANRParsers(); 
	@Override
	protected void addParsers() {
		addParser(TipoRubro.BIEN, RubroBienParser.class);
		addParser(TipoRubro.CONSULTOR, RubroConsultorParser.class);
		addParser(TipoRubro.INSUMO, RubroInsumoParser.class);
		addParser(TipoRubro.RECURSO_HUMANO_ADICIONAL, RubroRecursoHumanoAdicionalParser.class);
		addParser(TipoRubro.RECURSO_HUMANO_PROPIO, RubroRecursoHumanoPropioParser.class);
	}

}
