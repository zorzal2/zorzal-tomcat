package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.DesembolsoUFFABean;
import com.pragma.data.genericdao.GenericDao;

/**
 * 
 * @author ttoth
 * 
 */
public interface DesembolsoUFFADAO extends GenericDao<DesembolsoUFFABean, Long> {

	List<DesembolsoUFFABean> findByPac(Long idPac);

	List<DesembolsoUFFABean> findByPacCuota(Long idPac, Integer cuota);

	Object selectMontoPagoAnticipo(Long idPac);

}
