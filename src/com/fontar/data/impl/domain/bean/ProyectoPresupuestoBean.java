package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import com.fontar.data.api.domain.Adjuntable;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.ActividadBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.seguridad.EncryptedObject;

/**
 * Un presupuesto contiene información del plan de ejecución del proyecto.
 * Contien un plan de gastos por rubros (ver PresupuestoRubroCollectionBean) y tambien
 * un plan de etapas y activades del proyecto (ver EtapaBean).
 * 
 * Los datos de montos del prespuestos por requerimientos de seguiridad estan encriptados.
 * Cuando se modifica un presupuesto se registra la causa del cambio en el concepto de fundamentación.   
 * 
 * @see com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean
 * @see com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean
 */
public class ProyectoPresupuestoBean extends Auditable implements Adjuntable {

	private Long id;
	private EncryptedObject montoTotal;
	private EncryptedObject montoSolicitado;
	private String fundamentacion;
	private PresupuestoRubroCollectionBean presupuestoRubros;
	private Set<EtapaBean> etapas;	
	private Set adjuntos = new HashSet();

	public String getFundamentacion() {
		return fundamentacion;
	}

	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getMontoSolicitado() {
		return (BigDecimal) montoSolicitado.getObject();
	}

	public void setMontoSolicitado(BigDecimal montoSolicitado) {
		if(this.montoSolicitado == null)
			this.montoSolicitado = new EncryptedObject(montoSolicitado,null,true);
		else
			this.montoSolicitado = this.montoSolicitado.update(montoSolicitado);
	}

	public BigDecimal getMontoTotal() {
		return (BigDecimal) montoTotal.getObject();
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		if(this.montoTotal == null)
			this.montoTotal= new EncryptedObject(montoTotal,null,true);
		else
			this.montoTotal = this.montoTotal.update(montoTotal);
	}

	public void setMontoPresupuestoSolicitado(EncryptedObject montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}

	public void setMontoPresupuestoTotal(EncryptedObject montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	public EncryptedObject getMontoPresupuestoSolicitado() {
		return this.montoSolicitado;
	}

	public EncryptedObject getMontoPresupuestoTotal() {
		return this.montoTotal;
	}
	
	public BigDecimal getMontoEmpresa() {
		BigDecimal auxTotal      = (BigDecimal) this.montoTotal.getObject();
		BigDecimal auxSolicitado = (BigDecimal) this.montoSolicitado.getObject(); 
		if (auxTotal != null) {
			return auxTotal.subtract(auxSolicitado);
		}
		else
			return auxTotal;

	}
	public String toString() {
		return 
		"PRESUPUESTO\n" +
		"===========\n" +
		"Por Rubro\n" +
		"---------\n" +
		presupuestoRubros + '\n'+
		"Etapas\n" +
		"------\n" +
		etapas;
	}

	public Set<EtapaBean> getEtapas() {
		return etapas;
	}

	public void setEtapas(Set<EtapaBean> etapas) {
		this.etapas = etapas;
	}

	public PresupuestoRubroCollectionBean getPresupuestoRubros() {
		return presupuestoRubros;
	}

	public void setPresupuestoRubros(PresupuestoRubroCollectionBean presupuestoRubros) {
		this.presupuestoRubros = presupuestoRubros;
	}

	public Set getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(Set presupuestos) {
		this.adjuntos = presupuestos;
	}

	/**
	 * Le pasa observaciones y avances al presupuesto dado.
	 * @param presupuestoNuevo
	 */
	public void propagarDatosA(ProyectoPresupuestoBean presupuestoNuevo) {
		Map<String, String> map = new Hashtable<String, String>();
		//obtengo todos los datos
		for(EtapaBean etapa : this.getEtapas()) {
			if(etapa.getObservaciones()!=null) map.put("observaciones:etapa:"+etapa.getNombre(), etapa.getObservaciones());
			if(etapa.getAvance()!=null)map.put("avance:etapa:"+etapa.getNombre(), etapa.getAvance());
			
			for(ActividadBean actividad : etapa.getActividades()) {
				if(actividad.getObservacion()!=null) map.put("observaciones:actividad:"+etapa.getNombre()+":"+actividad.getNombre(), actividad.getObservacion());
				if(actividad.getAvance()!=null) map.put("avance:actividad:"+etapa.getNombre()+":"+actividad.getNombre(), actividad.getAvance());
			}
		}
		//pego todos los datos
		for(EtapaBean etapa : presupuestoNuevo.getEtapas()) {
			String observacionesEtapa = map.get("observaciones:etapa:"+etapa.getNombre());
			if(observacionesEtapa!=null) etapa.setObservaciones(observacionesEtapa);
			String avanceEtapa = map.get("avance:etapa:"+etapa.getNombre());
			if(avanceEtapa!=null) etapa.setAvance(avanceEtapa);
			
			for(ActividadBean actividad : etapa.getActividades()) {
				String observacionesActividad = map.get("observaciones:actividad:"+etapa.getNombre()+":"+actividad.getNombre());
				if(observacionesActividad!=null) actividad.setObservacion(observacionesActividad);
				String avanceActividad = map.get("avance:actividad:"+etapa.getNombre()+":"+actividad.getNombre());
				if(avanceActividad!=null) actividad.setAvance(avanceActividad);
			}
		}
	}
}
