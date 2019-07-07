package com.fontar.proyecto.presupuesto.excel.parser.parsers.arai;

import com.fontar.data.impl.domain.codes.rubro.TipoRubro;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.RubroParsers;

public class RubroARAIParsers extends RubroParsers {

	public static final RubroARAIParsers instance = new RubroARAIParsers(); 
	@Override
	protected void addParsers() {
		addParser(TipoRubro.BIEN, RubroBienParser.class);
		addParser(TipoRubro.CONSULTOR, RubroConsultorParser.class);
		addParser(TipoRubro.INSUMO, RubroInsumoParser.class);
		addParser(TipoRubro.RECURSO_HUMANO_ADICIONAL, RubroRecursoHumanoAdicionalParser.class);
		addParser(TipoRubro.RECURSO_HUMANO_PROPIO, RubroRecursoHumanoPropioParser.class);
	}

}
