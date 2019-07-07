package com.fontar.data.impl.dao.ldap;

import java.util.Comparator;

import com.fontar.data.impl.domain.ldap.Permiso;

public class PermisoComparator  implements Comparator {

	public int compare(Object o1, Object o2) {
		Permiso permiso1 = (Permiso) o1;
		Permiso permiso2 = (Permiso) o2;
		return (permiso1.getModulo() + permiso1.getIdPermiso()).compareTo(permiso2.getModulo() + permiso2.getIdPermiso());
	}

}
