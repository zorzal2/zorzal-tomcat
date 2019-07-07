package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.FacturacionBean;
import com.pragma.data.genericdao.GenericDao;

public interface FacturacionDAO extends GenericDao<FacturacionBean, Long> {

	public List<FacturacionBean> findByID(Long idEntidadBeneficiaria);

}
