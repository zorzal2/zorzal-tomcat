package com.fontar.data.impl.domain.dto.proyecto.presupuesto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.plan.EtapaDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.PresupuestoRubroDTO;

public class ProyectoPresupuestoDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private Long id;

	private BigDecimal montoTotal;

	private BigDecimal montoSolicitado;

	private String fundamentacion;
	
	private Collection<PresupuestoRubroDTO> presupuestoRubros = new ArrayList<PresupuestoRubroDTO>();

	private Set<EtapaDTO> etapas = new HashSet<EtapaDTO>();	

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
		return montoSolicitado;
	}

	public void setMontoSolicitado(BigDecimal montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public Set<EtapaDTO> getEtapas() {
		return etapas;
	}

	public void setEtapas(Set<EtapaDTO> etapas) {
		this.etapas = etapas;
	}

	public Collection<PresupuestoRubroDTO> getPresupuestoRubros() {
		return presupuestoRubros;
	}

	public void setPresupuestoRubros(Collection<PresupuestoRubroDTO> presupuestoRubros) {
		this.presupuestoRubros = presupuestoRubros;
	}
}
