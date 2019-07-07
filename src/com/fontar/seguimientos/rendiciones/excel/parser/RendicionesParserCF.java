package com.fontar.seguimientos.rendiciones.excel.parser;

import java.math.BigDecimal;

import com.pragma.excel.parser.util.Field;


public class RendicionesParserCF extends BaseRendicionesParser {

	@Override
	protected Field[] getFields() {
		return RendicionesLabels.CamposCF;
	}

	@Override
	protected void cargarMontos(Item item) throws RendicionesValidationException {
		BigDecimal costoTotal = item.getNumber(RendicionesLabels.MONTO_TOTAL);
		item.bean.setMontoTotal(costoTotal);
		
		item.bean.setMontoParteRendicion(BigDecimal.ZERO);
		item.bean.setMontoContraparteRendicion(costoTotal);
		
		item.bean.setMontoParteEvaluacion(item.bean.getMontoParteRendicion());
		item.bean.setMontoContraparteEvaluacion(item.bean.getMontoContraparteRendicion());
	}

}
