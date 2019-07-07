package com.fontar.seguimientos.rendiciones.excel.parser;

import com.fontar.data.impl.domain.bean.SeguimientoBean;

public class RendicionesParserBuilder {
	public static BaseRendicionesParser buildFor(SeguimientoBean seguimiento) {
		if(seguimiento.getProyecto().getInstrumento().aplicaCargaAlicuotaCF()) {
			return new RendicionesParserCF();
		} else {
			return new RendicionesParserANR();
		}
	}
}
