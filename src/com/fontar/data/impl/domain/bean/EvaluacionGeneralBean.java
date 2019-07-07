package com.fontar.data.impl.domain.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.fontar.data.Constant;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacionFinanciera;
import com.pragma.util.StringUtil;

/**
 * Estos objetos representan evaluaciones del proyecto realizadas por personas evaluadoras.
 * A diferencia de las <code>EvaluacionBean</code> estas evaluaciones tienen asociado un circuito de workflow. 
 */
public class EvaluacionGeneralBean extends EvaluacionBean {

	private Boolean esAuditoriaContable;

	private Boolean esContable;

	private Boolean esEconomica;

	private Boolean esFinanciera;

	private Boolean esTecnica;

	private Boolean esVisitaTecnica;

	private Date fechaEntregaComprometida;

	private TipoEvaluacionFinanciera tipoEvaluacionFinanciera;

	private String codigoTipoEvaluacionFinanciera;

	private Set<EvaluacionEvaluadorBean> evaluadores;

	public EvaluacionGeneralBean() {
		this.esAuditoriaContable = false;
		this.esContable = false;
		this.esEconomica = false;
		this.esFinanciera = false;
		this.esTecnica = false;
		this.esVisitaTecnica = false;
		super.setEsDictamen(true);
	}

	public Boolean getEsAuditoriaContable() {
		return esAuditoriaContable;
	}

	public void setEsAuditoriaContable(Boolean esAuditoriaContable) {
		this.esAuditoriaContable = esAuditoriaContable;
	}

	public Boolean getEsContable() {
		return esContable;
	}

	public void setEsContable(Boolean esContable) {
		this.esContable = esContable;
	}

	public Boolean getEsEconomica() {
		return esEconomica;
	}

	public void setEsEconomica(Boolean esEconomica) {
		this.esEconomica = esEconomica;
	}

	public Boolean getEsFinanciera() {
		return esFinanciera;
	}

	public void setEsFinanciera(Boolean esFinanciera) {
		this.esFinanciera = esFinanciera;
	}

	public Boolean getEsTecnica() {
		return esTecnica;
	}

	public void setEsTecnica(Boolean esTecnica) {
		this.esTecnica = esTecnica;
	}

	public Boolean getEsVisitaTecnica() {
		return esVisitaTecnica;
	}

	public void setEsVisitaTecnica(Boolean esVisitaTecnica) {
		this.esVisitaTecnica = esVisitaTecnica;
	}

	public Date getFechaEntregaComprometida() {
		return fechaEntregaComprometida;
	}

	public void setFechaEntregaComprometida(Date fechaEntregaComprometida) {
		this.fechaEntregaComprometida = fechaEntregaComprometida;
	}

	public TipoEvaluacionFinanciera getTipoEvaluacionFinanciera() {
		return tipoEvaluacionFinanciera;
	}

	public void setTipoEvaluacionFinanciera(TipoEvaluacionFinanciera tipoEvaluacionFinanciera) {
		this.tipoEvaluacionFinanciera = tipoEvaluacionFinanciera;
		this.codigoTipoEvaluacionFinanciera = (tipoEvaluacionFinanciera != null) ? tipoEvaluacionFinanciera.getName()
				: null;
	}

	public Set<EvaluacionEvaluadorBean> getEvaluadores() {
		return evaluadores;
	}

	public void setEvaluadores(Set<EvaluacionEvaluadorBean> evaluadores) {
		this.evaluadores = evaluadores;
	}

	protected String getCodigoTipoEvaluacionFinanciera() {
		return codigoTipoEvaluacionFinanciera;
	}

	protected void setCodigoTipoEvaluacionFinanciera(String codigoTipoEvaluacionFinanciera) {
		this.codigoTipoEvaluacionFinanciera = codigoTipoEvaluacionFinanciera;
		if (codigoTipoEvaluacionFinanciera != null)
			this.setTipoEvaluacionFinanciera(TipoEvaluacionFinanciera.valueOf(codigoTipoEvaluacionFinanciera));
	}

	/**
	 * Determina si una <code>Evaluacion</code>
	 * es de tipo técnica(si esTecnica o esVisitaTecnica) o no.
	 * @param evaluacion
	 * @return Boolean
	 */
	public Boolean esTipoTecnica() {
		return esTecnica || esVisitaTecnica;
	}	
	
	/**
	 * Determina si una <code>Evaluacion</code>
	 * es de tipo contable(si esContable o esAuditoriaContable) o no.
	 * @param evaluacion
	 * @return Boolean
	 */
	public Boolean esTipoContable() {
		return esContable || esAuditoriaContable;
	}		
	
	/**
	 * Devuelve una cadena con los tipos de evaluación
	 * separadas por un guión.<br> 
	 * @param bean la evaluación que se analiza
	 * @return cadena de tipos de evaluación separadas por guión (técnica - contable - financiera ...).
	 */
	public String obtenerTiposDeEvaluacion() {

		Collection<String> tipos = new ArrayList<String>();
		
		if (this.getEsTecnica()) tipos.add(Constant.TiposEvaluacion.TECNICA);
		if (this.getEsVisitaTecnica()) tipos.add(Constant.TiposEvaluacion.VISITA_TECNICA);
		if (this.getEsContable()) tipos.add(Constant.TiposEvaluacion.CONTABLE);
		if (this.getEsAuditoriaContable()) tipos.add(Constant.TiposEvaluacion.AUDITORIA_CONTABLE);
		if (this.getEsEconomica()) tipos.add(Constant.TiposEvaluacion.ECONOMICA);
		if (this.getEsFinanciera()) tipos.add(Constant.TiposEvaluacion.FINANCIERA);
		
		Iterator iterator = tipos.iterator();
	
		return StringUtil.join(iterator," - ");
	}		
	
	public String labelTipoEvaluacion() {
		
		String tipo = null;
		
		if (this.getEsTecnica()) tipo = Constant.TiposEvaluacion.TECNICA;
		else if (this.getEsVisitaTecnica()) tipo = Constant.TiposEvaluacion.VISITA_TECNICA;
		else if (this.getEsContable()) tipo = Constant.TiposEvaluacion.CONTABLE;
		else if (this.getEsAuditoriaContable()) tipo = Constant.TiposEvaluacion.AUDITORIA_CONTABLE;
		
		return tipo;
	}
	
	// ES_AUDITORIA_CONTABLE 8 N CHAR (1)
	// ES_CONTABLE 7 N CHAR (1)
	// ES_ECONOMICA 4 N CHAR (1)
	// ES_FINANCIARA 5 N CHAR (1)
	// ES_TECNICA 2 N CHAR (1)
	// ES_VISITA_TECNICA 9 N CHAR (1)
	// FE_COMPROMETIDA 3 N DATE
	// ID_EVALUACION 1 1 N NUMBER (10)
	// TP_EVALUACION_FINANCIERA 6 Y VARCHAR2 (20)
}