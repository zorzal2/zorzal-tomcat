package com.fontar.data.impl.dao.hibernate;

public class VentanillaPermanenteQueryInvocationHandler extends InstrumentosGrantedQueryInvocationHandler {

	public VentanillaPermanenteQueryInvocationHandler() {
		this(SecurityFilter.FILTER_NAME,"VENTANILLAPERMANENTE-INVENTARIO");
	}

	
	public VentanillaPermanenteQueryInvocationHandler(String filterName, String permissionRequired) {
		super(filterName, permissionRequired);
	}

}
