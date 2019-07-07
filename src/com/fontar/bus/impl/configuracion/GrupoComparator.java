package com.fontar.bus.impl.configuracion;

import java.util.Comparator;

import com.fontar.data.impl.domain.ldap.GrupoAbstracto;

public class GrupoComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		GrupoAbstracto grupo1 = (GrupoAbstracto) o1;
		GrupoAbstracto grupo2 = (GrupoAbstracto) o2;
		return grupo1.getNombre().compareToIgnoreCase( grupo2.getNombre());
	}

}
