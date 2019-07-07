package com.fontar.bus.impl.configuracion;

import java.util.Comparator;

import com.fontar.data.impl.domain.bean.EvaluadorBean;

public class EvaluadorComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		EvaluadorBean e1 = (EvaluadorBean) o1;
		EvaluadorBean e2 = (EvaluadorBean) o2;
		return e1.getNombre().compareTo( e2.getNombre());
	}

}
