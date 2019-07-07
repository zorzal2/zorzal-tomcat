package com.fontar.data.impl.dao.hibernate;

import java.util.Collection;

import com.fontar.data.api.dao.CiiuDAO;
import com.fontar.data.impl.domain.bean.CiiuBean;
import com.pragma.data.genericdao.impl.GenericDaoHibernateImpl;

public class CiiuDaoImpl 
	extends GenericDaoHibernateImpl<CiiuBean, Long> 
	implements CiiuDAO {

	public CiiuDaoImpl() {
		super(CiiuBean.class);
	}

	@SuppressWarnings("unchecked")
	public Collection<CiiuBean> getCiiuElegibles() {
		return getSession().createQuery("select o from CiiuBean o where o not in (select c.padre from CiiuBean c where c.padre is not null)").list();
	}

}
