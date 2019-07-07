package com.fontar.data.impl.dao.ldap;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.fontar.data.impl.domain.ldap.GrupoAbstracto;

public class NotInFilter implements Predicate {

	private Set ids;
	
	
	public NotInFilter(String[] idList){
		this.ids = new HashSet();
		CollectionUtils.addAll(ids,idList);
	}
	public boolean evaluate(Object object) {
		GrupoAbstracto grupo = (GrupoAbstracto) object;
		return !ids.contains(grupo.getIdGrupo());
	}

}
