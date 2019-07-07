package com.fontar.data.impl.dao.hibernate;

public class IdeaProyectoPitecQueryInvocationHandler extends InstrumentosGrantedQueryInvocationHandler {

	public IdeaProyectoPitecQueryInvocationHandler(String filterName, String permissionRequired) {
		super(filterName, permissionRequired);
	}

	public IdeaProyectoPitecQueryInvocationHandler() {
		this(SecurityFilter.FILTER_NAME,"IDEASPROYECTOPITEC-INVENTARIO");
	}

}
