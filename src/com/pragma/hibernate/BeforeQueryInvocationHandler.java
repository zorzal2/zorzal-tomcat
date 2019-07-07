package com.pragma.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;

public interface BeforeQueryInvocationHandler {

	public abstract String getFilterName();

	public abstract void setFilterName(String filterName);

	public abstract Query adapt(Query namedQuery, Session session);

}