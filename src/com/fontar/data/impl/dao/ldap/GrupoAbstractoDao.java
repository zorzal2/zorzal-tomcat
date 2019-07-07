package com.fontar.data.impl.dao.ldap;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.ldap.ContextMapper;
import org.springframework.ldap.LdapOperations;
import org.springframework.ldap.support.DistinguishedName;
import org.springframework.ldap.support.filter.AndFilter;
import org.springframework.ldap.support.filter.EqualsFilter;
import org.springframework.ldap.support.filter.OrFilter;

import com.fontar.data.impl.domain.ldap.GrupoAbstracto;

/**
 * Deberia reemplazar a los demas daos de grupos, no hace falta
 * distinguir por cada clase
 * 
 * @author mrouaux
 *
 */
public class GrupoAbstractoDao  {

	
	/**
     * The template object that performs all data access work.
     */
    private LdapOperations ldapOperations;
    
    private ContextMapper contextMapper;
    
	
    private  ContextMapper getContextMapper(){
		if(this.contextMapper ==null ){
			CompositeContextMapper contextMapper = new CompositeContextMapper();
	    	contextMapper.addMapper( "grupoFontar" , new GrupoContextMapper(ldapOperations));
	    	contextMapper.addMapper( "grupoInstrumentoFontar" , new GrupoConInstrumentoContextMapper(ldapOperations));
	    	this.contextMapper = contextMapper;
		}
		return this.contextMapper;
	}
    
    
	public LdapOperations getLdapOperations() {
		return ldapOperations;
	}




	public void setLdapOperations(LdapOperations ldapOperations) {
		this.ldapOperations = ldapOperations;
	}




	public GrupoAbstracto get(String name){
	    DistinguishedName dn = new DistinguishedName("ou=Grupos");
	    dn.add("idGrupo", name);
        return (GrupoAbstracto) ldapOperations.lookup(dn, this.getContextMapper());
	}
	
	public Set<GrupoAbstracto> get(String[] groupList){
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<GrupoAbstracto> getGroupMembership(String userDn) {
		AndFilter filter = new AndFilter();
		OrFilter classFilter = new OrFilter();
		classFilter.or(new EqualsFilter("objectclass", "grupoInstrumentoFontar"));
		classFilter.or(new EqualsFilter("objectclass", "grupoFontar"));
        filter.and( classFilter );
        filter.and(new EqualsFilter("usuario", userDn));
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), this.getContextMapper() );
	}
	
	
	public GrupoAbstracto findByName(String name){
		AndFilter filter = new AndFilter();
		OrFilter classFilter = new OrFilter();
		classFilter.or(new EqualsFilter("objectclass", "grupoInstrumentoFontar"));
		classFilter.or(new EqualsFilter("objectclass", "grupoFontar"));
        filter.and( classFilter );
        filter.and(new EqualsFilter("nombre", name));
        List groups = ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), this.getContextMapper() );
        return (GrupoAbstracto) (groups.isEmpty()? null : groups.get(0)); 
	}
	
}
