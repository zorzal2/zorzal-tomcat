package com.fontar.seguridad;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

import com.fontar.data.impl.domain.ldap.GrupoAbstracto;
import com.fontar.data.impl.domain.ldap.Permiso;

/**
 * @author mrouaux
 *
 */
public class GroupUpdateEvent  {

	
	private Collection<Permiso> disjuntion;
	
	@SuppressWarnings("unchecked")
	public GroupUpdateEvent(GrupoAbstracto grupo, Collection<Permiso> assignedPermissions){
		this.disjuntion = CollectionUtils.disjunction(grupo.getPermisos(),assignedPermissions);
	}

	public Collection<Permiso> getDisjuntion() {
		return disjuntion;
	}

	public void setDisjuntion(Collection<Permiso> disjuntion) {
		this.disjuntion = disjuntion;
	}
	
	public  boolean hasWorkflowChanges(){
		return CollectionUtils.countMatches(this.disjuntion, new WorkflowPermissionPredicate() ) > 0;
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<String> getDisjuntionNames() {
		return CollectionUtils.collect( this.disjuntion, new NameProjection());
	}

	
}
