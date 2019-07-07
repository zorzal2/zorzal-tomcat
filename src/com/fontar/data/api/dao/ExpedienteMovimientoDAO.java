package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ExpedienteMovimientoBean;
import com.pragma.data.genericdao.GenericDao;

public interface ExpedienteMovimientoDAO extends GenericDao<ExpedienteMovimientoBean, Long> {
	public List<ExpedienteMovimientoBean> findPorIdDePersona(Long idPersona);
}
