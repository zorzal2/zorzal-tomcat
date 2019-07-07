package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.EspecialidadEvaluadorBean;
import com.pragma.data.genericdao.GenericDao;

public interface EspecialidadEvaluadorDAO extends GenericDao<EspecialidadEvaluadorBean, Long> {

	public List<EspecialidadEvaluadorBean> findByCodigo(String codigo);

	public List<EspecialidadEvaluadorBean> findByNombre(String nombre);

	public List<EspecialidadEvaluadorBean> findByCodigoID(String codigo, Long idEspecialidad);

	public List<EspecialidadEvaluadorBean> findByNombreID(String nombre, Long idEspecialidad);

}