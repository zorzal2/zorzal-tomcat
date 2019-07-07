package com.fontar.data.impl.dao.hibernate;

public class SeguimientoQueryInvocationHandler extends EvaluacionQueryInvocationHandler {

	public SeguimientoQueryInvocationHandler() {
		this(SecurityFilter.FILTER_NAME,"SEGUIMIENTOS-INVENTARIO");
	}
	
	public SeguimientoQueryInvocationHandler(String filterName, String permissionRequired) {
		super(filterName, permissionRequired);
	}

}
