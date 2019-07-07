package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.VentanillaPermanenteBean;
import com.pragma.data.genericdao.GenericDao;

public interface VentanillaPermanenteDAO extends GenericDao<VentanillaPermanenteBean, Long> {

	List<VentanillaPermanenteBean> findByEstado(String estado);

	List<VentanillaPermanenteBean> findByIdentificador(String identificador);

	List<VentanillaPermanenteBean> findByIdentificadorId(String identificador, Long idVentanilla);

}