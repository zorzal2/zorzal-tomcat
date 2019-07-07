package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.PersonaBean;
import com.pragma.data.genericdao.GenericDao;

public interface PersonaDAO extends GenericDao<PersonaBean, Long> {

	public List<PersonaBean> findByCuit(String cuit);

	public List<PersonaBean> findByName(String name);
	
	public List<PersonaBean> findByUserId(String userId);
	
	public List<PersonaBean> findWithUserId();
}
