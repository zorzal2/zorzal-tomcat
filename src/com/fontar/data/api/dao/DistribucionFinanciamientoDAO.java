package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.DistribucionFinanciamientoBean;
import com.pragma.data.genericdao.GenericDao;

public interface DistribucionFinanciamientoDAO extends GenericDao<DistribucionFinanciamientoBean, Long> {

	List<DistribucionFinanciamientoBean> findByInstrumento(Long idInstrumento);

}
