package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.InstrumentoVersionBean;
import com.pragma.data.genericdao.GenericDao;

public interface InstrumentoVersionDAO extends GenericDao<InstrumentoVersionBean, Long> {

	List<InstrumentoVersionBean> findByInstrumento(Long idInstrumento);
}
