package com.fontar.data.impl.dao.hibernate;

public class LLamadoConvocatariaQueryInvocationHandler extends InstrumentosGrantedQueryInvocationHandler {

	public LLamadoConvocatariaQueryInvocationHandler() {
		this(SecurityFilter.FILTER_NAME,"LLAMADOSCONVOCATORIA-INVENTARIO");
	}
	
	public LLamadoConvocatariaQueryInvocationHandler(String filterName, String permissionRequired) {
		super(filterName, permissionRequired);
	}

}
