package com.fontar.data.impl.dao.hibernate;

public class ProyectoQueryInvocationHandler extends InstrumentosGrantedQueryInvocationHandler {

	public ProyectoQueryInvocationHandler(String filterName, String permissionRequired) {
		super(filterName, permissionRequired);
	}

	public ProyectoQueryInvocationHandler(){
		this(SecurityFilter.FILTER_NAME,"PROYECTOS-INVENTARIO");
	}
}
