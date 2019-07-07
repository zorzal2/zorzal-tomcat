package com.fontar.data.impl.dao.hibernate;

public class EvaluacionSeguimientoQueryInvocationHandler extends EvaluacionQueryInvocationHandler {

	public EvaluacionSeguimientoQueryInvocationHandler() {
		this(SecurityFilter.FILTER_NAME,"EVALUACIONESSEGUIMIENTO-INVENTARIO");
	}
	
	public EvaluacionSeguimientoQueryInvocationHandler(String filterName, String permissionRequired) {
		super(filterName, permissionRequired);
	}

}
