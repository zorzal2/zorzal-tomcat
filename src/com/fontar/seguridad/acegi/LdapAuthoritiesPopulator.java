package com.fontar.seguridad.acegi;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.ldap.InitialDirContextFactory;
import org.acegisecurity.ldap.LdapDataAccessException;
import org.acegisecurity.providers.ldap.populator.DefaultLdapAuthoritiesPopulator;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;

import com.fontar.data.impl.dao.ldap.GrupoConInstrumentoDao;
import com.fontar.data.impl.dao.ldap.GrupoDao;
import com.fontar.data.impl.domain.ldap.Grupo;
import com.fontar.data.impl.domain.ldap.GrupoConInstrumento;
import com.fontar.data.impl.domain.ldap.Permiso;

public class LdapAuthoritiesPopulator  implements org.acegisecurity.providers.ldap.LdapAuthoritiesPopulator {

	DefaultLdapAuthoritiesPopulator ldapAuthoritiesPopulator;

	private GrupoDao grupoDao;
	private GrupoConInstrumentoDao grupoConInstrumentoDao;
	
	public LdapAuthoritiesPopulator(InitialDirContextFactory initialDirContextFactory, String groupSearchBase) {
		super();
		this.ldapAuthoritiesPopulator = new DefaultLdapAuthoritiesPopulator(initialDirContextFactory,groupSearchBase);
	}
	 
	
	public GrupoDao getGrupoDao() {
		return grupoDao;
	}

	public void setGrupoDao(GrupoDao grupoDao) {
		this.grupoDao = grupoDao;
	}

	@SuppressWarnings("unchecked")
	public GrantedAuthority[] getGrantedAuthorities(LdapUserDetails userDetails) throws LdapDataAccessException {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		Collection<Grupo> grupos = this.getGrupoDao().getGroupMembership( userDetails.getDn() );
		for (Grupo grupo: grupos){
			for(Permiso permiso : grupo.getPermisos()) {
				authorities.add( new GrantedSimplePermission( permiso.getIdPermiso()) );
			}
		}
		Collection<GrupoConInstrumento> gruposConInstrumento = grupoConInstrumentoDao.getGroupMembership( userDetails.getDn() );
		for (GrupoConInstrumento grupoConInstrumento: gruposConInstrumento){
			for(Permiso permiso : grupoConInstrumento.getPermisos()) {
				authorities.add( new GrantedPermissionByInstrumento( permiso.getIdPermiso(), grupoConInstrumento.getIdInstrumento()) );
			}
		}
		return (GrantedAuthority[]) authorities.toArray(new GrantedAuthority[authorities.size()]);
		
	}
	
	

	public boolean equals(Object obj) {
		return ldapAuthoritiesPopulator.equals(obj);
	}

	public Set getGroupMembershipRoles(String userDn, String username) {
		return ldapAuthoritiesPopulator.getGroupMembershipRoles(userDn, username);
	}

	public int hashCode() {
		return ldapAuthoritiesPopulator.hashCode();
	}

	public void setConvertToUpperCase(boolean convertToUpperCase) {
		ldapAuthoritiesPopulator.setConvertToUpperCase(convertToUpperCase);
	}

	public void setDefaultRole(String defaultRole) {
		ldapAuthoritiesPopulator.setDefaultRole(defaultRole);
	}

	public void setGroupRoleAttribute(String groupRoleAttribute) {
		ldapAuthoritiesPopulator.setGroupRoleAttribute(groupRoleAttribute);
	}

	public void setGroupSearchFilter(String groupSearchFilter) {
		ldapAuthoritiesPopulator.setGroupSearchFilter(groupSearchFilter);
	}

	public void setRolePrefix(String rolePrefix) {
		ldapAuthoritiesPopulator.setRolePrefix(rolePrefix);
	}

	public void setSearchSubtree(boolean searchSubtree) {
		ldapAuthoritiesPopulator.setSearchSubtree(searchSubtree);
	}

	public String toString() {
		return ldapAuthoritiesPopulator.toString();
	}


	public void setGrupoConInstrumentoDao(GrupoConInstrumentoDao grupoConInstrumentoDao) {
		this.grupoConInstrumentoDao = grupoConInstrumentoDao;
	}

}
