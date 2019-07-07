package com.fontar.data.impl.dao.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;

public class IdeaProyectoQueryInvocationHandler extends InstrumentosGrantedQueryInvocationHandler {

	public IdeaProyectoQueryInvocationHandler(String filterName, String permissionRequired) {
		super(filterName, permissionRequired);
	}
	
	public IdeaProyectoQueryInvocationHandler() {
		this(SecurityFilter.FILTER_NAME,"IDEASPROYECTO-INVENTARIO");
	}
	
	public Query adapt(Query namedQuery, Session session) {
		//Tiene el permiso granteado
		if(this.getAuthorizationService()
				.grantedSimplePermission(this.getPermissionRequired()))
			return namedQuery;
		else
			throw new SecurityException("Permission required");
	}

}
