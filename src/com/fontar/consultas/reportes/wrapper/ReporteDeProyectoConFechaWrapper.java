package com.fontar.consultas.reportes.wrapper;

import java.util.Date;

import com.fontar.data.impl.domain.bean.ProyectoBean;

public class ReporteDeProyectoConFechaWrapper extends ReporteDeProyectoWrapper {

	private Date fecha;
	public ReporteDeProyectoConFechaWrapper(ProyectoBean proyecto, Date fecha) {
		super(proyecto);
		this.fecha = fecha;
	}
	public Date getFecha() {
		return fecha;
	}

}
