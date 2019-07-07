package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.pragma.data.genericdao.GenericDao;

public interface InstrumentoDAO extends GenericDao<InstrumentoBean, Long> {

	List<InstrumentoBean> findByComision();

	List<InstrumentoBean> findBySecretaria();

	List<InstrumentoBean> findByDirectorio();

	List<InstrumentoBean> findAllActivos();

	List<InstrumentoBean> findByIdentificador(String identificador);
}
