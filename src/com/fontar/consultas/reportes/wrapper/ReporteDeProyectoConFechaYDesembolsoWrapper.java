package com.fontar.consultas.reportes.wrapper;

import java.util.Date;

import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDesembolsoBean;
import com.pragma.toolbar.util.DateUtils;

public class ReporteDeProyectoConFechaYDesembolsoWrapper extends ReporteDeProyectoWrapper {
	private ProyectoDesembolsoBean desembolso;
	private Date fechaIncumplida;
	
	public ReporteDeProyectoConFechaYDesembolsoWrapper(ProyectoBean proyecto, Date fechaAnticipo, ProyectoDesembolsoBean desembolso) {
		super(proyecto);
		fechaIncumplida = DateUtils.addDays(fechaAnticipo, desembolso.getPlazo());
		this.desembolso = desembolso;
	}

	public ProyectoDesembolsoBean getDesembolso() {
		return desembolso;
	}

	public Date getFechaIncumplida() {
		return fechaIncumplida;
	}
}
