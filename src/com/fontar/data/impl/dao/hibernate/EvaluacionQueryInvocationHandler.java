package com.fontar.data.impl.dao.hibernate;

import java.util.Collection;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.fontar.data.impl.dao.ldap.GrupoConInstrumentoDao;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.pragma.util.ContextUtil;

public class EvaluacionQueryInvocationHandler extends InstrumentosGrantedQueryInvocationHandler {

	public EvaluacionQueryInvocationHandler(String filterName, String permissionRequired) {
		super(filterName, permissionRequired);
	}
	
	public EvaluacionQueryInvocationHandler() {
		this(SecurityFilter.FILTER_NAME,"EVALUACIONES-INVENTARIO");
	}


	@SuppressWarnings("unchecked")
	private Collection getAllowedProjects(){
		GrupoConInstrumentoDao dao = (GrupoConInstrumentoDao) ContextUtil.getBean("grupoConInstrumentoLdapDao");
		String userDn = ((LdapUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getDn();
		Collection allowedInstances  =  dao.instrumentosGranted( userDn , this.getPermissionRequired() ); 
		if( allowedInstances.isEmpty())
			allowedInstances.add("-1"); 
		return allowedInstances;
	}
	
	@SuppressWarnings("unchecked")
	public Collection getAllowedProjects(Session session){
		SecurityFilter securityFilter = new SecurityFilter( session.enableFilter( "evaluacionSecurityFilter" ) );
		securityFilter.setAllowedInstances( this.getAllowedProjects() );
		Criteria criteria = session.createCriteria(ProyectoRaizBean.class);
		criteria.setProjection(Projections.id());
		Collection instances =  criteria.list();
		session.disableFilter("evaluacionSecurityFilter" );
		if( instances.isEmpty())
			instances.add("-1"); 
		
        return instances;
	}
	
	@SuppressWarnings("unchecked")
	public Query adapt(Query namedQuery, Session session) {
		//Tiene el permiso granteado
		if(!this.allGranted()){
			Collection allowedProjects = this.getAllowedProjects(session);
			SecurityFilter securityFilter = new SecurityFilter( session.enableFilter( this.getFilterName() ) );
			securityFilter.setAllowedInstances( allowedProjects );
		}
		return namedQuery;
	}
}
