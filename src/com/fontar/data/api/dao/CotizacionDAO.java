package com.fontar.data.api.dao;

import java.util.Date;
import java.util.List;

import com.fontar.data.impl.domain.bean.CotizacionBean;
import com.pragma.data.genericdao.GenericDao;
import com.pragma.util.annotation.HasNamedParams;
import com.pragma.util.annotation.ParamName;

/**
 * 
 * @author ttoth, llobeto
 * 
 */
@HasNamedParams 
public interface CotizacionDAO extends GenericDao<CotizacionBean, Long> {

	List<CotizacionBean> findByMoneda(Long idMoneda);
	/**
	 * Devuelve las cotizaciones vigentes para la moneda. Solo devuelve
	 * mas de una cuando se ingresaron dos diferentes para la misma fecha.
	 * @param idMoneda
	 * @param fecha
	 * @return
	 */
	List<CotizacionBean> findVigente(@ParamName("idMoneda") Long idMoneda, @ParamName("fecha") Date fecha);

}
