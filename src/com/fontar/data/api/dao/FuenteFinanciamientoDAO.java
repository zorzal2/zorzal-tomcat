package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.FuenteFinanciamientoBean;
import com.pragma.data.genericdao.GenericDao;

public interface FuenteFinanciamientoDAO extends GenericDao<FuenteFinanciamientoBean, Long> {

	public List<FuenteFinanciamientoBean> findByIdentificador(String identificador);

	public List<FuenteFinanciamientoBean> findByIdentificadorId(String identificador, Long idFuente);

	public List<FuenteFinanciamientoBean> findByDenominacion(String denominacion);

	public List<FuenteFinanciamientoBean> findByDenominacionId(Long idFuente, String denominacion);

}