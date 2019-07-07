package com.fontar.data.impl.dao.hibernate;

public class PaqueteQueryInvocationHandler extends InstrumentosGrantedQueryInvocationHandler {

	public PaqueteQueryInvocationHandler(String filterName, String permissionRequired) {
		super(filterName, permissionRequired);
	}
	
	public PaqueteQueryInvocationHandler(){
		this(SecurityFilter.FILTER_NAME,"PAQUETES-INVENTARIO");
	}
}
