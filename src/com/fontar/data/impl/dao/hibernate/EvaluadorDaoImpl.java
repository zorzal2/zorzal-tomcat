package com.fontar.data.impl.dao.hibernate;

import java.util.List;

import com.fontar.data.api.dao.EvaluadorDAO;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.pragma.data.genericdao.impl.GenericDaoHibernateImpl;

public class EvaluadorDaoImpl 
	extends GenericDaoHibernateImpl<EvaluadorBean, Long> implements EvaluadorDAO {

	public EvaluadorDaoImpl() {
		super(EvaluadorBean.class);
	}

	@Override
	public boolean tieneBorradoLogico() {
		return true;
	}

	@Override
	protected String whereBorrado(String varName, boolean borrado) {
		// o.borrado=b or o.persona.borrado=b				
		StringBuilder ret = new StringBuilder();
		ret.append(varName);
		ret.append(".borrado=");
		ret.append(borrado);
		ret.append(" or ");
		ret.append(varName);
		ret.append(".persona.borrado=");
		ret.append(borrado);
		return ret.toString();
	}

	@SuppressWarnings("unchecked")
	public List<EvaluadorBean> findByIdDePersona(Long idPersona) {
		return (List<EvaluadorBean>) 
			getResults("select e from EvaluadorBean e where e.persona.id = ? and e.borrado=false", idPersona);
	}
}
