package com.pragma.data.genericdao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.hibernate.Query;
import org.hibernate.Session;

import com.fontar.data.impl.dao.hibernate.AllInstrumentosGrantedQueryInvocationHandler;
import com.pragma.hibernate.BeforeQueryInvocationHandler;

public class AllowedInstancesDaoImpl<T, PK extends Serializable> extends GenericDaoHibernateImpl<T, PK> {

	private BeforeQueryInvocationHandler queryInvocationHandler;
	
	public AllowedInstancesDaoImpl(Class<T> type) {
		super(type);
		this.queryInvocationHandler = new AllInstrumentosGrantedQueryInvocationHandler();
		this.queryInvocationHandler.setFilterName("securityFilter");
	}


	public BeforeQueryInvocationHandler getQueryInvocationHandler() {
		return queryInvocationHandler;
	}

	
	public void setQueryInvocationHandler(BeforeQueryInvocationHandler queryInvocationHandler) {
		this.queryInvocationHandler = queryInvocationHandler;
	}


	@Override
	protected Query prepareQuery(Method method, Object[] queryArgs) {
		
		final String queryName = getNamingStrategy().queryNameFromMethod(this.type, method);

		//Security Filter
		Session session = getSession();
		
		
		final Query namedQuery = session.getNamedQuery(queryName);
		String[] namedParameters = namedQuery.getNamedParameters();

		if (namedParameters.length == 0) {
			this.setPositionalParams(queryArgs, namedQuery);
		}
		else {
			this.setNamedParams(namedParameters, queryArgs, namedQuery);
		}

		return this.queryInvocationHandler.adapt(namedQuery, session);
	}

}
