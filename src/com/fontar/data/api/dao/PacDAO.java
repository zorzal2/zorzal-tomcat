package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.PacBean;
import com.pragma.data.genericdao.GenericDao;

/**
 * 
 * @author ttoth
 * 
 */
public interface PacDAO extends GenericDao<PacBean, Long> {

	List<PacBean> findByProyecto(Long idProyecto);

	List<PacBean> findByTipoAdquisicionYProyecto(Long idTipoAdquisicion, Long idProyecto);
	
	List<PacBean> findByProcedimiento(Long idProcedimiento);
}
