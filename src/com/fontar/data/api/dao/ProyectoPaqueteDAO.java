package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoPaqueteBean;
import com.pragma.data.genericdao.GenericDao;

public interface ProyectoPaqueteDAO extends GenericDao<ProyectoPaqueteBean, Long> {
	List<ProyectoPaqueteBean> findByProyectoPaquete(Long idProyecto, Long idPaquete);
	List<ProyectoPaqueteBean> findByProyecto(Long idProyecto);
}
