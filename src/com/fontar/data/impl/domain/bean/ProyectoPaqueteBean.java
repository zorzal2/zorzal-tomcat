package com.fontar.data.impl.domain.bean;

/**
 * Estos objetos representan la vinculación entre proyectos y paquetes.
 * Una vinculación, en un momento dado, ser desactivada.
 * En un paquete, cada proyecto contiene una evaluación que se conforma mediante las acciones del paquete.  
 *  
 * @see com.fontar.data.impl.domain.bean.PaqueteBean
 * @see com.fontar.data.impl.domain.bean.ProyectoBean
 * @see com.fontar.data.impl.domain.bean.EvaluacionPaqueteBean
 */
public class ProyectoPaqueteBean extends Auditable {

	private Long id;

	private Long idPaquete;

	private Boolean esActivo;

	private Long idProyecto;

	private ProyectoBean proyecto;

	private PaqueteBean paquete;
	
	private EvaluacionPaqueteBean evaluacionPaquete;

	public PaqueteBean getPaquete() {
		return paquete;
	}

	public void setEvaluacion(EvaluacionPaqueteBean e) {
		this.evaluacionPaquete = e;
	}

	public EvaluacionPaqueteBean getEvaluacion() {
		return evaluacionPaquete;
	}

	public void setPaquete(PaqueteBean paquete) {
		this.paquete = paquete;
	}

	public ProyectoBean getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoBean proyecto) {
		this.proyecto = proyecto;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPaquete() {
		return idPaquete;
	}

	public void setIdPaquete(Long idPaquete) {
		this.idPaquete = idPaquete;
	}

	public Boolean getEsActivo() {
		return esActivo;
	}

	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}
}