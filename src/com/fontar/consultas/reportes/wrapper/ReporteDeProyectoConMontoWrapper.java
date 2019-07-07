package com.fontar.consultas.reportes.wrapper;

import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.pragma.util.StringUtil;

public class ReporteDeProyectoConMontoWrapper extends ReporteDeProyectoWrapper {

	private String monto;
	public ReporteDeProyectoConMontoWrapper(ProyectoBean proyecto, Number monto) {
		super(proyecto);
		this.monto = StringUtil.formatMoneyForPresentation(monto);
	}
	public String getMonto() {
		return monto;
	}
}
