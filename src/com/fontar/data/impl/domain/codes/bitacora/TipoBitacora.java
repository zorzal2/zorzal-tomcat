package com.fontar.data.impl.domain.codes.bitacora;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

public enum TipoBitacora implements Enumerable {
	MANUAL("app.codes.bitacora.tipoBitacora.manual"),
	BASICO("app.codes.bitacora.tipoBitacora.basico"),
	PROY_DATOS("app.codes.bitacora.tipoBitacora.proyectoDatos"),
	PROY_PAQUETE("app.codes.bitacora.tipoBitacora.proyectoPaquete"),
	ADMISION("app.codes.bitacora.tipoBitacora.admision"),
	EVALUACION("app.codes.bitacora.tipoBitacora.evaluacion"),
	ENTIDAD_INTERVINIENTE("app.codes.bitacora.tipoBitacora.entidadInterviniente"), 
	PROY_JURISDICCION("app.codes.bitacora.tipoBitacora.proyectoJurisdiccion"),
	PRESUPUESTO("app.codes.bitacora.tipoBitacora.presupuesto"),
	EXPEDIENTE("app.codes.bitacora.tipoBitacora.expediente"),
	MOV_EXPEDIENTE("app.codes.bitacora.tipoBitacora.expedienteMovimiento"),
	RECONSIDERACION("app.codes.bitacora.tipoBitacora.reconsideracion"),
	SEGUIMIENTO("app.codes.bitacora.tipoBitacora.seguimiento");
	
	private String descripcion;

	private TipoBitacora(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}

	@Override
	public String toString() {
		return getDescripcion();
	}
	
}
