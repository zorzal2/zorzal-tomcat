package com.fontar.bus.impl.configuracion;

import java.util.Collection;

import com.fontar.bus.api.configuracion.EvaluadorServicio;
import com.fontar.data.api.dao.EvaluadoresDAO;
import com.fontar.data.impl.domain.bean.EvaluadorBean;

/**
 * @author gboaglio
 * 
 */

public class EvaluadorServicioImpl implements EvaluadorServicio {

	EvaluadoresDAO evaluadorDao;

	public void setEvaluadorDao(EvaluadoresDAO evaluadorDao) {
		this.evaluadorDao = evaluadorDao;
	}

	public Collection getEvaluadores() {
		return evaluadorDao.getEvaluadores();
	}

	public EvaluadorBean storeEvaluador(EvaluadorBean evaluador) {
		EvaluadorBean nuevoEvaluador = this.evaluadorDao.storeEvaluador(evaluador);
		return nuevoEvaluador;
	}

	public EvaluadorBean getEvaluadorById(String id) {
		return evaluadorDao.getEvaluadorById(id);
	}

	public EvaluadorBean updateEvaluador(EvaluadorBean evaluadorBean) {
		EvaluadorBean modificadoEvaluador = this.evaluadorDao.updateEvaluador(evaluadorBean);
		return modificadoEvaluador;
	}

	public void deleteEvaluador(EvaluadorBean evaluadorBean) {
		evaluadorDao.deleteEvaluador(evaluadorBean);
	}
}
