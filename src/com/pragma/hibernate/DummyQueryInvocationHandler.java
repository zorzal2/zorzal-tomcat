package com.pragma.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;

public class DummyQueryInvocationHandler extends AbstractQueryInvocationHandler  {

	
	public DummyQueryInvocationHandler(){
		super("");
	}
	
	public DummyQueryInvocationHandler(String filterName) {
		super(filterName);
	}

	public Query adapt(Query namedQuery, Session session) {
		return namedQuery;
	}

}
