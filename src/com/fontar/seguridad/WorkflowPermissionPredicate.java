package com.fontar.seguridad;

import org.apache.commons.collections.Predicate;

import com.fontar.data.impl.domain.ldap.Permiso;

public class WorkflowPermissionPredicate implements Predicate {

	public boolean evaluate(Object object) {
		Permiso permiso = (Permiso) object;
		return permiso.getIdPermiso().startsWith("WF");
	}
}