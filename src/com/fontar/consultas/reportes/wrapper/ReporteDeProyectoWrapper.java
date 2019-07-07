package com.fontar.consultas.reportes.wrapper;

import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;

public class ReporteDeProyectoWrapper {
	ProyectoBean proyecto;
	public ReporteDeProyectoWrapper(ProyectoBean proyecto) {
		this.proyecto = proyecto;
	}
	public String getCodigo() {
		return proyecto.getCodigo();
	}
	public InstrumentoBean getInstrumento() {
		return proyecto.getInstrumento();
	}
	public ProyectoDatosBean getProyectoDatos() {
		return proyecto.getProyectoDatos();
	}
	public ProyectoBean getProyectoOriginal() {
		return proyecto;
	}
}
