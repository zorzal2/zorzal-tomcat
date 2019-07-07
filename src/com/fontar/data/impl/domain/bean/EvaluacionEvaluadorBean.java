package com.fontar.data.impl.domain.bean;

/**
 * Estos objetos representan los evaluadores asignados en las evaluaciones (EvaluacionGeneralBean). 
 * La informacion de los evaluadores se consdera confidencial 
 * y por este motivo esta información se registra en forma encriptada.
 * @see com.fontar.data.impl.domain.bean.EvaluacionGeneralBean 
 */
public class EvaluacionEvaluadorBean {

	private Long id;

	private Long idEvaluacion;

	private byte[] institucionData;

	private String institucion;

	private byte[] evaluadorData;

	private String evaluador;

	private byte[] lugarEvaluacionData;

	private String lugarEvaluacion;

	public String getEvaluador() {
		return evaluador;
	}

	public void setEvaluador(String evaluador) {
		this.evaluador = evaluador;
	}

	public byte[] getEvaluadorData() {
		return evaluadorData;
	}

	public void setEvaluadorData(byte[] evaluadorData) {
		this.evaluadorData = evaluadorData;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEvaluacion() {
		return idEvaluacion;
	}

	public void setIdEvaluacion(Long idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public byte[] getInstitucionData() {
		return institucionData;
	}

	public void setInstitucionData(byte[] institucionData) {
		this.institucionData = institucionData;
	}

	public String getLugarEvaluacion() {
		return lugarEvaluacion;
	}

	public void setLugarEvaluacion(String lugarEvaluacion) {
		this.lugarEvaluacion = lugarEvaluacion;
	}

	public byte[] getLugarEvaluacionData() {
		return lugarEvaluacionData;
	}

	public void setLugarEvaluacionData(byte[] lugarEvaluacionData) {
		this.lugarEvaluacionData = lugarEvaluacionData;
	}
	public Long getIdEvaluador() {
		if(evaluador==null || evaluador.length()==0) return null;
		else return new Long(evaluador);
	}
	public void setIdEvaluador(Long idEvaluador) {
		if(idEvaluador==null) evaluador = null;
		else evaluador = idEvaluador.toString();
	}
}
