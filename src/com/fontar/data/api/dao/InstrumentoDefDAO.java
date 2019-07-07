package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.InstrumentoDefBean;
import com.pragma.data.genericdao.GenericDao;

public interface InstrumentoDefDAO extends GenericDao<InstrumentoDefBean, Long> {

	public List<InstrumentoDefBean> findByIdentificador(String identificador);

	public List<InstrumentoDefBean> findByIdentificadorId(Long idInstrumentoDef, String identificador);

}
