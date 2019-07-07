package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.NotificacionBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.pragma.data.genericdao.GenericDao;

public interface NotificacionDAO extends GenericDao<NotificacionBean, Long> {

	List<ProyectoRaizBean> findByNotificacionesConProyectos();
	
	List<NotificacionBean> findByID(Long id);
	
}
