package com.fontar.data.impl.domain.dto;


public class PaqueteEvaluarResultadoProyectoDTO {
/**
 * Guardo los datos de los proyectos.  La clave es idProyecto
 */
	private Long	idProyecto;
	private String	resultado;
	private String  dictamenStr;
	private boolean dictamen;
	private String	fundamentacion;
	private String	alicuota;
	private Long[]	idEvaluaciones;
	private Long[]	idEvaluacionesNoElegibles;

	public String getFundamentacion() {
		return fundamentacion;
	}
	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
	}
	public Long[] getIdEvaluaciones() {
		return idEvaluaciones;
	}
	public void setIdEvaluaciones(Long[] idEvaluaciones) {
		this.idEvaluaciones = idEvaluaciones;
	}
	public Long[] getIdEvaluacionesNoElegibles() {
		return idEvaluacionesNoElegibles;
	}
	public void setIdEvaluacionesNoElegibles(Long[] idEvaluacionesNoElegibles) {
		this.idEvaluacionesNoElegibles = idEvaluacionesNoElegibles;
	}
	public Long getIdProyecto() {
		return idProyecto;
	}
	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public boolean getDictamen() {
		return dictamen;
	}
	public void setDictamen(boolean dictamen) {
		this.dictamen = dictamen;
	}
	public String getDictamenStr() {
		return dictamenStr;
	}
	public void setDictamenStr(String dictamenStr) {
		this.dictamenStr = dictamenStr;
		if(dictamenStr.equals("on")) {
			this.setDictamen(true);
		}
	}
	public String getAlicuota() {
		return alicuota;
	}
	public void setAlicuota(String alicuota) {
		this.alicuota = alicuota;
	}
}