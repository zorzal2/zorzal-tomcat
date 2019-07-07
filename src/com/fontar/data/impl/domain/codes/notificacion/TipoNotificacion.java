package com.fontar.data.impl.domain.codes.notificacion;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de los tipos de <code>Notificacion</code>.<br>
 * @author ssanchez
 */
public enum TipoNotificacion implements Enumerable {

	RESULT_JNTA_APROB("resultadoJuntaAprobado",false), 
	RESULT_JNTA_NO_APROB("resultadoJuntaNoAprobado",true),
	ADMISION_APROBADA("admisionAprobada",false),
	ADMISION_RECHAZADA("admisionRechazada",true),
	READMISION_APROBADA("reAdmisionAprobada",false),
	READMISION_RECHAZADA("reAdmisionRechazada",true),
	CONFORMIDAD_EVAL("conformidadEvaluacion",true),
	ADJUDICACION("adjudicacion",null),
	POSI_RECONSIDERACION("conPosibilidadReconsideracion",null);
	
	private String descripcion;
	private Boolean requiereAcuse;
	
	private TipoNotificacion(String key, Boolean requiereAcuse) {
		this.descripcion = ResourceManager.getCodesResource("app.codes.notificacion.tipo." + key);
		this.requiereAcuse = requiereAcuse; 
	}
	public Boolean getRequiereAcuse() {
		return requiereAcuse;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getName() {
		return this.name();
	}
	@Override
	public String toString() {
		return this.descripcion;
	}
}
