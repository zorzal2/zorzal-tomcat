package com.fontar.data.impl.dao.hibernate;

public class PresentacionConvocatoriaQueryInvocationHandler extends InstrumentosGrantedQueryInvocationHandler {

	public PresentacionConvocatoriaQueryInvocationHandler(){
		this(SecurityFilter.FILTER_NAME,"PRESENTACIONCONVOCATORIA-INVENTARIO");
	}
	
	public PresentacionConvocatoriaQueryInvocationHandler(String filterName, String permissionRequired) {
		super(filterName, permissionRequired);
	}

}
