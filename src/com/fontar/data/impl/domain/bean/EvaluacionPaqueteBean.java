package com.fontar.data.impl.domain.bean;

/**
 * Estos objetos representan evaluaciones del proyecto cuando el mismo es tratado en paquete.
 * 
 * @see com.fontar.data.impl.domain.bean.ProyectoPaqueteBean
 * @see com.fontar.data.impl.domain.bean.PaqueteBean  
 */
public class EvaluacionPaqueteBean extends EvaluacionBean {

	private Long idProyectoPaquete;

	public Long getIdProyectoPaquete() {
		return idProyectoPaquete;
	}

	public void setIdProyectoPaquete(Long idProyectoPaquete) {
		this.idProyectoPaquete = idProyectoPaquete;
	}
}
