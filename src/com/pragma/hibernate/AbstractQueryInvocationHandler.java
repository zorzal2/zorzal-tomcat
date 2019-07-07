package com.pragma.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;

import com.fontar.seguridad.AuthenticationService;
import com.fontar.seguridad.AuthorizationService;
import com.pragma.util.ContextUtil;

public abstract class  AbstractQueryInvocationHandler implements BeforeQueryInvocationHandler {

	private String filterName;

	
	public AbstractQueryInvocationHandler(String filterName) {
		super();
		this.filterName = filterName;
	}

	/* (non-Javadoc)
	 * @see com.pragma.hibernate.BeforeQueryInvocationHandler#getFilterName()
	 */
	public String getFilterName() {
		return filterName;
	}

	/* (non-Javadoc)
	 * @see com.pragma.hibernate.BeforeQueryInvocationHandler#setFilterName(java.lang.String)
	 */
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public abstract Query adapt(Query namedQuery, Session session);

	
	protected AuthenticationService getAuthenticationService(){
		return (AuthenticationService) ContextUtil.getBean("authenticationService");
	}
	
	protected AuthorizationService getAuthorizationService(){
		return (AuthorizationService) ContextUtil.getBean("authorizationService");
	}
}
