package com.fontar.data.impl.dao.hibernate;

import java.util.Collection;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fontar.data.impl.dao.ldap.GrupoConInstrumentoDao;
import com.pragma.hibernate.AbstractQueryInvocationHandler;
import com.pragma.util.ContextUtil;

public  class  InstrumentosGrantedQueryInvocationHandler extends AbstractQueryInvocationHandler {

	private String permissionRequired;
	
	public InstrumentosGrantedQueryInvocationHandler(String filterName, String permissionRequired) {
		super(filterName);
		this.permissionRequired = permissionRequired;
	}


	public String getPermissionRequired() {
		return permissionRequired;
	}

	public void setPermissionRequired(String permissionRequired) {
		this.permissionRequired = permissionRequired;
	}

	public Collection getAllowedInstances(){
		GrupoConInstrumentoDao dao = (GrupoConInstrumentoDao) ContextUtil.getBean("grupoConInstrumentoLdapDao");
		String userDn = ((LdapUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getDn();
		return dao.instrumentosGranted( userDn , this.getPermissionRequired() );
	}
	
	protected Boolean allGranted(){
		return this.getAuthorizationService().grantedSimplePermission(this.getPermissionRequired());
	}

	/* (non-Javadoc)
	 * @see com.pragma.hibernate.BeforeQueryInvocationHandler#adapt(org.hibernate.Query, org.hibernate.Session)
	 */
	@SuppressWarnings("unchecked")
	public Query adapt(Query namedQuery, Session session) {
		//Tiene el permiso granteado
		if(!this.allGranted()){
			
			//Grupos que contienen le permiso requerido
			Collection allowedInstances = this.getAllowedInstances( );
			if( allowedInstances.isEmpty())
				allowedInstances.add("-1"); //FIXME, esto esta muy malo
			SecurityFilter securityFilter = new SecurityFilter( session.enableFilter( this.getFilterName() ) );
			securityFilter.setAllowedInstances( allowedInstances );
		}
		return namedQuery;
	}
	
}
