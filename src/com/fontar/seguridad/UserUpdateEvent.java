package com.fontar.seguridad;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.fontar.data.impl.domain.ldap.GrupoAbstracto;
import com.fontar.data.impl.domain.ldap.Usuario;

public class UserUpdateEvent {

	
	String userName;
	Collection<GrupoAbstracto> disjuntion;
	
	@SuppressWarnings("unchecked")
	public UserUpdateEvent(Usuario usuario, Collection<GrupoAbstracto> assignedGroups ){
		this.userName = usuario.getUsername();
		this.disjuntion = CollectionUtils.disjunction(usuario.getGrupos(),assignedGroups);
	}

	

	public UserUpdateEvent(String userName, Collection<GrupoAbstracto> disjuntion) {
		super();
		this.userName = userName;
		this.disjuntion = disjuntion;
	}



	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Collection<GrupoAbstracto> getDisjuntion() {
		return disjuntion;
	}


	public void setDisjuntion(Collection<GrupoAbstracto> disjuntion) {
		this.disjuntion = disjuntion;
	}


	public Collection<GrupoAbstracto> getGroupsDisjuntion() {
		return this.getDisjuntion();
	}
	
	public boolean hasGroupChanges(){
		return !this.getDisjuntion().isEmpty();
	}


	public boolean hasWorkflowChanges() {
		return CollectionUtils.countMatches(this.disjuntion, new WorkflowPermission() ) > 0;
	}
	
	private class WorkflowPermission implements Predicate {

		public boolean evaluate(Object object) {
			GrupoAbstracto grupo = (GrupoAbstracto) object;
			return CollectionUtils.countMatches(grupo.getPermisos(), new WorkflowPermissionPredicate() ) > 0;
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<String> getPermissionDisjuntion() {
		Collection permissions = new HashSet();
		for (GrupoAbstracto grupo : this.disjuntion)
			permissions.addAll(CollectionUtils.collect(grupo.getPermisos(),new NameProjection()));
		return permissions;
	}
}
