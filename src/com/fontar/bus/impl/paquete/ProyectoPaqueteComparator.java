package com.fontar.bus.impl.paquete;

import java.util.Comparator;

import com.fontar.data.impl.domain.bean.ProyectoPaqueteBean;

public class ProyectoPaqueteComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		ProyectoPaqueteBean e1 = (ProyectoPaqueteBean) o1;
		ProyectoPaqueteBean e2 = (ProyectoPaqueteBean) o2;
		return e1.getProyecto().getCodigo().compareTo( e2.getProyecto().getCodigo());
	}

}
