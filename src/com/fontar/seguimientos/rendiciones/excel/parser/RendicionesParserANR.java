package com.fontar.seguimientos.rendiciones.excel.parser;

import java.math.BigDecimal;

import com.fontar.seguimientos.rendiciones.message.RendicionesMessages;
import com.pragma.excel.parser.util.Field;


public class RendicionesParserANR extends BaseRendicionesParser {

	@Override
	protected Field[] getFields() {
		return RendicionesLabels.CamposANR;
	}

	@Override
	protected void cargarMontos(Item item) throws RendicionesValidationException {
		BigDecimal costoFontar = item.getNumber(RendicionesLabels.MONTO_FONTAR);
		item.bean.setMontoParteRendicion(costoFontar);
		BigDecimal costoContraparte = item.getNumber(RendicionesLabels.MONTO_CONTRAPARTE);
		item.bean.setMontoContraparteRendicion(costoContraparte);
		//Si es un recurso humano propio no puede haber monto fontar
		if(item.bean.getRubro().getEsRecursoHumanoPropio() && !costoFontar.equals(BigDecimal.ZERO)) {
			throw new RendicionesValidationException(RendicionesMessages.costoNoImputable(item.bean));
		}
		item.bean.setMontoTotal(costoFontar.add(costoContraparte));
		
		item.bean.setMontoParteEvaluacion(item.bean.getMontoParteRendicion());
		item.bean.setMontoContraparteEvaluacion(item.bean.getMontoContraparteRendicion());
	}
}
