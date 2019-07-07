package com.fontar.bus.api.configuracion;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.dto.MonedaDTO;

public class FaltanCotizacionesException extends Exception {

	private static final long serialVersionUID = 1L;
	private Map<Long, MonedaDTO> monedasSinCotizacion = new HashMap<Long, MonedaDTO>(4);
	
	public void agregarMoneda(MonedaDTO moneda) {
		if(monedasSinCotizacion.containsKey(moneda.getId())) return;
		monedasSinCotizacion.put(moneda.getId(), moneda);
	}
	public Collection<MonedaDTO> monedasSinCotizacion() {
		return monedasSinCotizacion.values();
	}
}
