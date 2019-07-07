package com.fontar.bus.api.proyecto.desembolso;

import java.math.BigDecimal;

public class MontoElegidoSuperaMontoDelBeneficio extends Exception {
	private static final long serialVersionUID = 1L;
	private BigDecimal montoBeneficio;
	private BigDecimal montoElegido;
	public MontoElegidoSuperaMontoDelBeneficio(BigDecimal montoBeneficio, BigDecimal montoElegido) {
		super();
		this.montoBeneficio = montoBeneficio;
		this.montoElegido = montoElegido;
	}
	public BigDecimal getMontoBeneficio() {
		return montoBeneficio;
	}
	public BigDecimal getMontoElegido() {
		return montoElegido;
	}
	
}
