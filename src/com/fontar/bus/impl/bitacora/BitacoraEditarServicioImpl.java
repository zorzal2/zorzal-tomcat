package com.fontar.bus.impl.bitacora;

import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.pragma.bus.impl.GenericServiceImpl;

public class BitacoraEditarServicioImpl extends GenericServiceImpl {

	public BitacoraEditarServicioImpl(Class type) {
		super(type);
	}

	public Object save(Object bean) {

		BitacoraBean bitacora = (BitacoraBean) bean;
		saveBean(bitacora);

		return null;// Esto lo hago solo porque me lo pide la interfaz
	}

}
