package com.fontar.data.impl.domain.bean;
import java.math.BigDecimal;
/**
 * Estos objetos registran información información relacionada con la facturación de las Entidades Beneficiarias. 
 * @see EntidadBeneficariaBean
 */
public class FacturacionBean {

	private Long id;

	private Long idEntidadBeneficiaria;

	private Integer numeroPeriodico;

	private BigDecimal numeroFacturacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getNumeroFacturacion() {
		return numeroFacturacion;
	}

	public void setNumeroFacturacion(BigDecimal numeroFacturacion) {
		this.numeroFacturacion = numeroFacturacion;
	}

	public Integer getNumeroPeriodico() {
		return numeroPeriodico;
	}

	public void setNumeroPeriodico(Integer numeroPeriodico) {
		this.numeroPeriodico = numeroPeriodico;
	}

	public Long getIdEntidadBeneficiaria() {
		return idEntidadBeneficiaria;
	}

	public void setIdEntidadBeneficiaria(Long idEntidadBeneficiaria) {
		this.idEntidadBeneficiaria = idEntidadBeneficiaria;
	}

}
