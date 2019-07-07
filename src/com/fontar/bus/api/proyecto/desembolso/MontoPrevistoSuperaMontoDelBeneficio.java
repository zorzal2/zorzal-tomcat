package com.fontar.bus.api.proyecto.desembolso;

import java.math.BigDecimal;


public class MontoPrevistoSuperaMontoDelBeneficio extends MontoElegidoSuperaMontoDelBeneficio {
	private static final long serialVersionUID = 1L;
	public MontoPrevistoSuperaMontoDelBeneficio(BigDecimal montoBeneficio, BigDecimal montoElegido) {
		super(montoBeneficio, montoElegido);
	}
}
