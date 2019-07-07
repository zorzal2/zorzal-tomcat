package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.CambioEstadoProyecto;
import com.pragma.data.genericdao.GenericDao;

public interface CambioEstadoProyectoDao extends GenericDao<CambioEstadoProyecto, Long> {

	
	List<CambioEstadoProyecto> findByConvocatoria(Long idConvocatoria);
}
