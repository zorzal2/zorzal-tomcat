package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.pragma.data.genericdao.GenericDao;

public interface ProyectoRaizDAO extends GenericDao<ProyectoRaizBean, Long> {
	public List<? extends ProyectoRaizBean> findProyectosPorIdDePersonaParticipante(Long idPersona);
}
