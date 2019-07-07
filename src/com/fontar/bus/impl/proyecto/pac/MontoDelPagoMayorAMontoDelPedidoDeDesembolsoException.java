package com.fontar.bus.impl.proyecto.pac;

import com.pragma.bus.BusinessException;

public class MontoDelPagoMayorAMontoDelPedidoDeDesembolsoException extends BusinessException {
	private static final long serialVersionUID = 1L;
	

	public MontoDelPagoMayorAMontoDelPedidoDeDesembolsoException() {
		super();
		setBundleKey("app.proyecto.pac.montoDePagoMayorAMontoDelPedidoDeDesembolso");
	}
}
