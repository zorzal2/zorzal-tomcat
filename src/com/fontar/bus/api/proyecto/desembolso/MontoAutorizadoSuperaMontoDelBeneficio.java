package com.fontar.bus.api.proyecto.desembolso;

import java.math.BigDecimal;

public class MontoAutorizadoSuperaMontoDelBeneficio extends MontoElegidoSuperaMontoDelBeneficio {
	private static final long serialVersionUID = 1L;
	public MontoAutorizadoSuperaMontoDelBeneficio(BigDecimal montoBeneficio, BigDecimal montoElegido) {
		super(montoBeneficio, montoElegido);
	}
}
