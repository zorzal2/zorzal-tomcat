package com.fontar.bus.api.proyecto.desembolso;

import java.math.BigDecimal;

public class MontoDesembolsadoSuperaMontoDelBeneficio extends MontoElegidoSuperaMontoDelBeneficio {
	private static final long serialVersionUID = 1L;
	public MontoDesembolsadoSuperaMontoDelBeneficio(BigDecimal montoBeneficio, BigDecimal montoElegido) {
		super(montoBeneficio, montoElegido);
	}
}
