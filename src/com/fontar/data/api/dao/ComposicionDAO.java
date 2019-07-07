package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ComposicionBean;
import com.pragma.data.genericdao.GenericDao;

public interface ComposicionDAO extends GenericDao<ComposicionBean, Long> {

	public List<ComposicionBean> findByID(Long idEntidadBeneficiaria);

}
