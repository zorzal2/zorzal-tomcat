package com.fontar.data.api.dao;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.CiiuBean;
import com.pragma.data.genericdao.GenericDao;

public interface CiiuDAO extends GenericDao<CiiuBean, Long> {
	public Collection<CiiuBean> getCiiuElegibles();
}