package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ClavesBean;
import com.pragma.data.genericdao.GenericDao;

public interface ClavesBeanDAO extends GenericDao<ClavesBean, Long>{
	public ClavesBean selectByUserAndDataType(String dataType, String user);
	public List<ClavesBean> findByUser(String user);
}
