package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean;
import com.pragma.data.genericdao.GenericDao;

public interface DistribucionTipoProyectoDAO extends GenericDao<DistribucionTipoProyectoBean, Long> {

	List<DistribucionTipoProyectoBean> findByInstrumento(Long idInstrumento);
	List<DistribucionTipoProyectoBean> findByInstrumentoTipoProyecto(Long idTipoProyecto,Long idInstrumento);
}
