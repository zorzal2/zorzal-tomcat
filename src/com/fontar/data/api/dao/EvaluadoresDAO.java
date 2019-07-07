package com.fontar.data.api.dao;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.EvaluadorBean;

/**
 * 
 * @author gboaglio
 * 
 */

public interface EvaluadoresDAO {

	public Collection getEvaluadores();

	public EvaluadorBean storeEvaluador(EvaluadorBean evaluador);

	public EvaluadorBean getEvaluadorById(String id);

	public EvaluadorBean updateEvaluador(EvaluadorBean evaluadorBean);

	public void deleteEvaluador(EvaluadorBean evaluadorBean);

}
